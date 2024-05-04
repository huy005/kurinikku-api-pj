package springbootpj.clinicpj.repositories;

import java.util.List;

import springbootpj.clinicpj.dtos.ScheduleDetailsDto;
import springbootpj.clinicpj.dtos.SpecificSchedule;
import springbootpj.clinicpj.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query("select new springbootpj.clinicpj.dtos.ScheduleDetailsDto" +
            "(s.scheduleDate, s.scheduleTime) " +
            "from UserSchedule as us " +
            "inner join us.user as u " +
            "inner join us.schedule as s " +
            "where u.email = :email")
    List<ScheduleDetailsDto> findDoctorScheduleDetailsDtos(@Param("email") String email);

    @Query("select new springbootpj.clinicpj.dtos.SpecificSchedule" +
            "(s.updatedAt, s.deletedAt, s.doctor) " +
            "from Schedule as s " +
            "where s.scheduleId = :id")
    SpecificSchedule getSpecificSchedule(@Param("id") String id);


    @Query("select new springbootpj.clinicpj.dtos.ScheduleDetailsDto" +
            "(s.scheduleDate, s.scheduleTime) " +
            "from UserSchedule as us " +
            "inner join us.user as u " +
            "inner join us.schedule as s " +
            "where u.userId = :id")
    List<ScheduleDetailsDto> getScheduleDetailsDtos(@Param("id") String id);
}
