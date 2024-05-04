package springbootpj.clinicpj.repositories;

import springbootpj.clinicpj.entities.Specialization;
import springbootpj.clinicpj.dtos.OutstandingSpecializationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

    @Query("select new springbootpj.clinicpj.dtos.OutstandingSpecializationDto" +
            "(spe.specializationId, spe.specializationName, spe.specializationDescription, count(d.doctor)) " +
            "from DoctorPatient as d " +
            "inner join d.specialization as spe " +
            "group by spe.specializationId " +
            "order by count(d.doctor) DESC " +
            "limit 5")
    List<OutstandingSpecializationDto> findSpecializationsBySchedule();

    @Query("select s.specializationName " +
            "from Specialization as s " +
            "where s.specializationName " +
            "like %:specializationName%")
    List<Specialization> findByKeyWord(@Param("specializationName") String specializationName);
}
