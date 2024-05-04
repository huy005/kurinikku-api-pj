package springbootpj.clinicpj.services;

import springbootpj.clinicpj.dtos.DoctorScheduleDto;
import springbootpj.clinicpj.dtos.UpdatingScheduleDto;
import springbootpj.clinicpj.entities.Schedule;
import org.springframework.security.core.Authentication;

public interface DoctorService {
    Schedule findById(int id);

    DoctorScheduleDto getDoctorSchedules(String id);

    boolean updateSchedule(UpdatingScheduleDto updatingScheduleDto,
                           Authentication authentication);
}
