package springbootpj.clinicpj.services;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.util.ByteArrayDataSource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import springbootpj.clinicpj.dtos.ExtraInfoDto;
import springbootpj.clinicpj.dtos.GenericResponse;
import springbootpj.clinicpj.dtos.PatientDetailsDto;
import springbootpj.clinicpj.dtos.PatientExtraInfoDto;
import springbootpj.clinicpj.exception.UserNotFoundException;
import springbootpj.clinicpj.helpers.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import springbootpj.clinicpj.repositories.ExtraInfoRepository;
import springbootpj.clinicpj.repositories.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class JasperReportsImp implements JasperReportsService {

    private ExtraInfoRepository extraInfoRepository;
    private ApplicationContext context;
    private UserRepository userRepository;


    @Autowired
    public JasperReportsImp(UserRepository userRepository,
                            ExtraInfoRepository extraInfoRepository,
                            ApplicationContext context) {
        this.extraInfoRepository = extraInfoRepository;
        this.context = context;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<GenericResponse> generrateRT(String id) {

        try {
//            Get Template
            Resource resource = context.getResource("classpath:patientExtraInfoRP.jrxml");
            InputStream inputStream = resource.getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

//            Set Parameters, Datasource

            PatientDetailsDto patientDetailsDto = userRepository.findPatientDetailsDto(id);
            List<ExtraInfoDto> extraInfoDtos = extraInfoRepository.getExtraInfoDetailsDto(id);
            PatientExtraInfoDto patientExtraInfoDto = new PatientExtraInfoDto();
            List<PatientExtraInfoDto> patientExtraInfoDtoList = new ArrayList<>();
            if (patientDetailsDto != null && !extraInfoDtos.isEmpty()) {
                patientExtraInfoDto.setUserName(patientDetailsDto.getUserName());
                patientExtraInfoDto.setUserBirthDay(patientDetailsDto.getUserBirthDay());
                patientExtraInfoDto.setUserGender(patientDetailsDto.getUserGender());
                patientExtraInfoDto.setEmail(patientDetailsDto.getEmail());
                patientExtraInfoDto.setUserAddress(patientDetailsDto.getUserAddress());
                for (int i=0; i<extraInfoDtos.size(); i++) {
                    patientExtraInfoDto.setHistoryBreath(extraInfoDtos.get(i).getHistoryBreath());
                    patientExtraInfoDto.setMoreInfo(extraInfoDtos.get(i).getMoreInfo());
                    patientExtraInfoDto.setCreatedAt(extraInfoDtos.get(i).getCreatedAt());
                    patientExtraInfoDtoList.add(patientExtraInfoDto);
                }
            }
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("title", "Patient's Extra Info");

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(patientExtraInfoDtoList);

//            Create PDF file and send to email
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            byte[] reportContent = JasperExportManager.exportReportToPdf(jasperPrint);
            DataSource byteArrayDataSource = new ByteArrayDataSource(reportContent, "application/pdf");

            Properties properties = new Properties();
            properties.put("mail.smtp.auth", true);
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("your email address", "your email password");
                }
            });
//            Create a default MimeMessage object.
            Message message = new MimeMessage(session);
//            Sender's email ID needs to be mentioned
            message.setFrom(new InternetAddress("test-email@gmail.com"));
//            Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("your email address"));
//            Set Subject: header field
            message.setSubject("Test Mail Subject");

//            Create the message part
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
//            Set the actual message
            mimeBodyPart.setText("This is your 'Patient Extra Info Report.'");
//            Create a multipart message
            Multipart multipart = new MimeMultipart();
//            Set text message part
            multipart.addBodyPart(mimeBodyPart);

//            Attach PDF to email
            mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setDataHandler(new DataHandler(byteArrayDataSource));
            mimeBodyPart.setFileName("patient-extra-info.pdf");
            multipart.addBodyPart(mimeBodyPart);

//            Send the complete message parts
            message.setContent(multipart);
//            Send message
            Transport.send(message);

            return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(),
                    "PDF file sent to the specified email.", Utils.getTimeStampHelper()));

        } catch (IOException | JRException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        throw new UserNotFoundException("Failed to send PDF file to the specified email.");
    }
}
