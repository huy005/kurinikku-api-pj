package springbootpj.clinicpj.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import springbootpj.clinicpj.dtos.*;
import springbootpj.clinicpj.entities.PasswordResetToken;
import springbootpj.clinicpj.entities.Schedule;
import springbootpj.clinicpj.entities.User;
import springbootpj.clinicpj.entities.UserSchedule;
import springbootpj.clinicpj.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordResetTokenRepository passwordResetTokenRepository;
    private SpecializationRepository specializationRepository;
    private ClinicRepository clinicRepository;
    private ScheduleRepository scheduleRepository;
    private UserScheduleRepository userScheduleRepository;
    private ExtraInfoRepository extraInfoRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordResetTokenRepository passwordResetTokenRepository,
                           SpecializationRepository specializationRepository,
                           ClinicRepository clinicRepository,
                           ScheduleRepository scheduleRepository,
                           UserScheduleRepository userScheduleRepository,
                           ExtraInfoRepository extraInfoRepository) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.specializationRepository = specializationRepository;
        this.clinicRepository = clinicRepository;
        this.scheduleRepository = scheduleRepository;
        this.userScheduleRepository = userScheduleRepository;
        this.extraInfoRepository = extraInfoRepository;
    }

    @Override
    public User findById(int id) {
        Optional<User> userResult = userRepository.findById(id);
        User theUser = null;
        if (userResult.isPresent()) theUser = userResult.get();
        return theUser;
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> userResult = userRepository.getUserByEmail(email);
        User theUser = null;
        if (userResult.isPresent()) theUser = userResult.get();
        return theUser;
    }

    @Override
    public PasswordResetToken findPasswordResetTokenByToken(String token) {
        Optional<PasswordResetToken> passwordResetTokenResult =
                passwordResetTokenRepository.findPasswordResetTokenByToken(token);
        PasswordResetToken thePasswordResetToken = null;
        if (passwordResetTokenResult.isPresent()) {
            thePasswordResetToken = passwordResetTokenResult.get();
            if (LocalDateTime.now().isBefore(thePasswordResetToken.getExpiryDate())) {
                return thePasswordResetToken;
            }
        }
        return null;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    //    Retieve email from authentication(Token)
    @Override
    public UserScheduleDto findUserScheduleDto(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        UserScheduleDto userScheduleDto = new UserScheduleDto();
        userScheduleDto.setUserDetailsDto(userRepository.findUserDetailsDto(user.getEmail()));
        userScheduleDto.setScheduleDetailsDtos(scheduleRepository.findDoctorScheduleDetailsDtos(user.getEmail()));
        return userScheduleDto;
    }

    @Override
    public List<DoctorInformationDto> findDoctorInformationDto(String id) {
        return userRepository.findDoctorInformationDto(id);
    }

    @Override
    public void updateDoctorDetails(DoctorScheduleDetailsDto doctorScheduleDetailsDto, User doctor) {
        UserSchedule userSchedule = new UserSchedule();
        userSchedule.setUser(doctor);
        Schedule schedule = new Schedule();
        schedule.setScheduleDate(doctorScheduleDetailsDto.getScheduleDate());
        schedule.setScheduleTime(doctorScheduleDetailsDto.getScheduleTime());
        schedule.setDoctor(doctor);
        schedule.setIsActive(1);
        schedule.setCreatedAt(LocalDateTime.now());
        userSchedule.setSchedule(schedule);
        userScheduleRepository.save(userSchedule);
        scheduleRepository.save(schedule);
    }

    @Override
    public List<ClinicSpecializationDto> findClinicWithKeywords(ClinicSpecializationDto clinicSpecializationDto) {
        List<ClinicSpecializationDto> clinics = clinicRepository.findClinicWithKeywords(clinicSpecializationDto.getClinicName(),
                clinicSpecializationDto.getClinicAddress(),
                clinicSpecializationDto.getClinicDescription(),
                clinicSpecializationDto.getClinicCost(),
                clinicSpecializationDto.getSpecializationName());
        return clinics;
    }



    @Override
    public boolean lockOrUnlock(ActiveUser activeUser) {
        String userEmail = activeUser.getEmail();
        User user = findByEmail(userEmail);
        if (user != null) {
            if (activeUser.getIsActive() == 1 && user.getIsActive() == 0) {
                user.setIsActive(1);
                userRepository.save(user);
                return true;
            } else if (activeUser.getIsActive() == 0 && user.getIsActive() == 1) {
                user.setIsActive(0);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}

