package springbootpj.clinicpj.services;

import org.springframework.security.core.Authentication;
import springbootpj.clinicpj.dtos.*;
import springbootpj.clinicpj.entities.PasswordResetToken;
import springbootpj.clinicpj.entities.User;

import java.util.List;

public interface UserService {
    User findById(int id);

    User findByEmail(String email);

    PasswordResetToken findPasswordResetTokenByToken(String token);

    void save(User user);

    UserScheduleDto findUserScheduleDto(Authentication authentication);

    List<DoctorInformationDto> findDoctorInformationDto(String id);

    void updateDoctorDetails(DoctorScheduleDetailsDto doctorScheduleDetailsDto, User user);

    List<ClinicSpecializationDto> findClinicWithKeywords(ClinicSpecializationDto clinicSpecializationDto);

    boolean lockOrUnlock(ActiveUser activeUser);
}
