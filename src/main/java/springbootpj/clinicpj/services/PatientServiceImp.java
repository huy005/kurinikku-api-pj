package springbootpj.clinicpj.services;


import springbootpj.clinicpj.dtos.*;
import springbootpj.clinicpj.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import springbootpj.clinicpj.repositories.*;

import java.util.List;

@Service
public class PatientServiceImp implements PatientService {

    private UserRepository userRepository;
    private ScheduleRepository scheduleRepository;
    private SpecializationRepository specializationRepository;
    private ClinicRepository clinicRepository;
    private ExtraInfoRepository extraInfoRepository;

    @Autowired
    public PatientServiceImp(UserRepository userRepository,
                             ScheduleRepository scheduleRepository,
                             ExtraInfoRepository extraInfoRepository,
                             SpecializationRepository specializationRepository,
                             ClinicRepository clinicRepository) {
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
        this.extraInfoRepository = extraInfoRepository;
        this.specializationRepository = specializationRepository;
        this.clinicRepository = clinicRepository;
    }

    @Override
    public List<OutstandingSpecializationDto> findSpecializationsBySchedule() {
        return specializationRepository.findSpecializationsBySchedule();
    }

    @Override
    public List<OutstandingClinicDto> findClinicsBySchedule() {
        return clinicRepository.findClinicsBySchedule();
    }

    @Override
    public PatientScheduleExtraInfoDto getPatientScheduleExtraInfoDto(String id) {
        UserRoleDetailsDto userRoleDetailsDto = userRepository.getUserRoleDetailsDto(id);
        List<ScheduleDetailsDto> scheduleDetailsDtos = scheduleRepository.getScheduleDetailsDtos(id);
        List<ExtraInfoDto> ExtraInfoDtos = extraInfoRepository.getExtraInfoDetailsDto(id);

        if (userRoleDetailsDto != null && !scheduleDetailsDtos.isEmpty()) {
            if (userRoleDetailsDto.getRole().getRoleId() == 2) {
                PatientDetailsDto patientDetailsDto = new PatientDetailsDto(userRoleDetailsDto.getUserName(),
                        userRoleDetailsDto.getUserBirthDay(),
                        userRoleDetailsDto.getUserGender(),
                        userRoleDetailsDto.getEmail(),
                        userRoleDetailsDto.getUserAddress());
                PatientScheduleExtraInfoDto patientScheduleExtraInfoDto =
                        new PatientScheduleExtraInfoDto(patientDetailsDto, scheduleDetailsDtos, ExtraInfoDtos);
                return patientScheduleExtraInfoDto;
            }
        }
        return null;
    }

    @Override
    public List<DoctorExtraInfoDto> getPatientExtraInfoDto(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        UserDetailsIdDto doctorInfo = userRepository.findUserDetailsIdDto(user.getEmail());
        List<DoctorExtraInfoDto> doctorExtraInfoDtos = extraInfoRepository.findPatientExtraInfoDtos(String.valueOf(doctorInfo.getUserId()));
        if (doctorInfo != null && !doctorExtraInfoDtos.isEmpty()) {
            return doctorExtraInfoDtos;
        }
        return null;
    }
}
