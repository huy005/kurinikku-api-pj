package springbootpj.clinicpj.repositories;

import springbootpj.clinicpj.dtos.ClinicSpecializationDto;
import springbootpj.clinicpj.entities.Clinic;
import springbootpj.clinicpj.dtos.OutstandingClinicDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
    @Query("select new springbootpj.clinicpj.dtos.OutstandingClinicDto" +
            "(cli.clinicId, cli.clinicName, cli.clinicAddress, cli.clinicDescription, count(d.doctor)) " +
            "from DoctorPatient as d " +
            "inner join d.clinic as cli " +
            "group by cli.clinicId " +
            "order by count(d.doctor) DESC " +
            "limit 5")
    List<OutstandingClinicDto> findClinicsBySchedule();

    @Query("select new springbootpj.clinicpj.dtos.ClinicSpecializationDto" +
            "(c.clinicName, c.clinicAddress, c.clinicDescription, c.clinicCost, s.specializationName) " +
            "from DoctorPatient as d " +
            "inner join d.clinic as c " +
            "inner join d.specialization as s " +
            "where c.clinicName = :clinicName " +
            "or c.clinicAddress like :clinicAddress " +
            "or c.clinicDescription like :clinicDescription " +
            "or c.clinicCost = :clinicCost " +
            "or s.specializationName = :specializationName")
    List<ClinicSpecializationDto> findClinicWithKeywords(@Param("clinicName") String clinicName,
                                                         @Param("clinicAddress") String clinicAddress,
                                                         @Param("clinicDescription") String clinicDescription,
                                                         @Param("clinicCost") String clinicCost,
                                                         @Param("specializationName") String specializationName);

}
