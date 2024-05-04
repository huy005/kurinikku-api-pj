package springbootpj.clinicpj.services;


import springbootpj.clinicpj.dtos.*;
import springbootpj.clinicpj.entities.Schedule;
import springbootpj.clinicpj.entities.User;
import springbootpj.clinicpj.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import springbootpj.clinicpj.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImp implements DoctorService {

    private UserRepository userRepository;
    private PasswordResetTokenRepository passwordResetTokenRepository;
    private SpecializationRepository specializationRepository;
    private ClinicRepository clinicRepository;
    private ScheduleRepository scheduleRepository;
    private UserScheduleRepository userScheduleRepository;
    private ExtraInfoRepository ExtraInfoRepository;

    @Autowired
    public DoctorServiceImp(UserRepository userRepository,
                            PasswordResetTokenRepository passwordResetTokenRepository,
                            SpecializationRepository specializationRepository,
                            ClinicRepository clinicRepository,
                            ScheduleRepository scheduleRepository,
                            UserScheduleRepository userScheduleRepository,
                            ExtraInfoRepository ExtraInfoRepository) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.specializationRepository = specializationRepository;
        this.clinicRepository = clinicRepository;
        this.scheduleRepository = scheduleRepository;
        this.userScheduleRepository = userScheduleRepository;
        this.ExtraInfoRepository = ExtraInfoRepository;
    }

    @Override
    public Schedule findById(int id) {
        Optional<Schedule> scheduleResult = scheduleRepository.findById(id);
        Schedule theSchedule = null;
        if (scheduleResult.isPresent()) theSchedule = scheduleResult.get();
        return theSchedule;
    }

    @Override
    public DoctorScheduleDto getDoctorSchedules(String id) {
        UserRoleDetailsDto userRoleDetailsDto = userRepository.getUserRoleDetailsDto(id);
        List<ScheduleDetailsDto> scheduleDetailsDtos = scheduleRepository.getScheduleDetailsDtos(id);

        if (userRoleDetailsDto != null && !scheduleDetailsDtos.isEmpty()) {
            if (userRoleDetailsDto.getRole().getRoleId() == 1) {
                DoctorDetailsDto doctorDetailsDto = new DoctorDetailsDto(userRoleDetailsDto.getUserName(),
                        userRoleDetailsDto.getUserBirthDay(),
                        userRoleDetailsDto.getUserGender(),
                        userRoleDetailsDto.getEmail(),
                        userRoleDetailsDto.getUserAddress());
                DoctorScheduleDto doctorScheduleDto = new DoctorScheduleDto(doctorDetailsDto, scheduleDetailsDtos);
                return doctorScheduleDto;
            } else {
                throw new UserNotFoundException("Data not found!");
            }
        }
        return new DoctorScheduleDto();
    }

    // 5.2.3
    @Override
    public boolean updateSchedule(UpdatingScheduleDto updatingScheduleDto,
                                  Authentication authentication) {
        User doctor = (User) authentication.getPrincipal();
        Schedule schedule = findById(updatingScheduleDto.getScheduleId());
        if (schedule != null && updatingScheduleDto.getIsReservedOrCancellation() == 1) {
            schedule.setUpdatedAt(LocalDateTime.now());
            schedule.setDeletedAt(null);
            schedule.setDoctor(doctor);
            schedule.setIsActive(1);
            scheduleRepository.save(schedule);
            return true;
        } else if (schedule != null && updatingScheduleDto.getIsReservedOrCancellation() == 0) {
            if (schedule.getDoctor().getUserId() == doctor.getUserId()) {
                schedule.setDeletedAt(LocalDateTime.now());
                schedule.setDoctor(null);
                schedule.setIsActive(0);
                scheduleRepository.save(schedule);
                return true;
            }
        }
        return false;
    }
}
