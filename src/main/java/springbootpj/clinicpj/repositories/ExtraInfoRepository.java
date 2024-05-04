package springbootpj.clinicpj.repositories;

import springbootpj.clinicpj.dtos.DoctorExtraInfoDto;
import springbootpj.clinicpj.dtos.ExtraInfoDto;
import springbootpj.clinicpj.entities.ExtraInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtraInfoRepository extends JpaRepository<ExtraInfo, Integer> {
    //    status:(1,"Waiting"), (2,"Processing"), (3,"Ending");
    @Query("select new springbootpj.clinicpj.dtos.DoctorExtraInfoDto" +
            "(u.userName, u.email, u.userBirthDay, u.userGender, u.userAddress,e.historyBreath, e.moreInfo) " +
            "from ExtraInfo as e " +
            "inner join e.patient as p " +
            "inner join p.doctor as d " +
            "inner join p.user as u " +
            "inner join p.status as s " +
            "where d.userId = :id " +
            "and s.statusId in (1,2)")
    List<DoctorExtraInfoDto> findPatientExtraInfoDtos(@Param("id") String id);

    @Query("select new springbootpj.clinicpj.dtos.ExtraInfoDto" +
            "(e.historyBreath, e.moreInfo, e.createdAt) " +
            "from ExtraInfo as e " +
            "inner join e.patient as p " +
            "inner join p.user as u " +
            "inner join u.role as r " +
            "where u.userId = :id ")
    List<ExtraInfoDto> getExtraInfoDetailsDto(@Param("id") String id);
}
