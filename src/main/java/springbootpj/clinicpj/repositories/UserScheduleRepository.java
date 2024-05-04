package springbootpj.clinicpj.repositories;

import springbootpj.clinicpj.entities.UserSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserScheduleRepository extends JpaRepository<UserSchedule, Integer> {
}
