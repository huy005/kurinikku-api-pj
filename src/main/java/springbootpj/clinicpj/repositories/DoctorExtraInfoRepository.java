package springbootpj.clinicpj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbootpj.clinicpj.entities.DoctorExtraInfo;

@Repository
public interface DoctorExtraInfoRepository extends JpaRepository<DoctorExtraInfo, Integer> {
}
