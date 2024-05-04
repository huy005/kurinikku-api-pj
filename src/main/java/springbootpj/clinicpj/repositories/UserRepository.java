package springbootpj.clinicpj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springbootpj.clinicpj.dtos.*;
import springbootpj.clinicpj.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> getUserByEmail(String theEmail);

    @Query("select new springbootpj.clinicpj.dtos.UserDetailsDto" +
            "(u.userName, u.email, u.userBirthDay, u.userGender,u.userAddress, u.userPhoneNumber, u.userDescription) " +
            "from User as u " +
            "where u.email = :email")
    UserDetailsDto findUserDetailsDto(@Param("email") String email);

    @Query("select new springbootpj.clinicpj.dtos.UserDetailsIdDto" +
            "(u.userId, u.userName, u.email, u.userBirthDay, u.userGender,u.userAddress, u.userPhoneNumber, u.userDescription) " +
            "from User as u " +
            "where u.email = :email")
    UserDetailsIdDto findUserDetailsIdDto(@Param("email") String email);

    @Query("select distinct new springbootpj.clinicpj.dtos.DoctorInformationDto" +
            "(u.userName, u.email, u.userAddress, u.userPhoneNumber," +
            " s.specializationName, s.specializationDescription) " +
            "from DoctorPatient as d " +
            "inner join d.specialization as s " +
            "inner join d.doctor as u " +
            "where s.specializationId = :id")
    List<DoctorInformationDto> findDoctorInformationDto(@Param("id") String id);

    @Query("select new springbootpj.clinicpj.dtos.UserRoleDetailsDto" +
            "(u.userName, u.email, u.userBirthDay, u.userGender,u.userAddress, u.userPhoneNumber, u.userDescription,u.role) " +
            "from User as u " +
            "where u.userId = :id")
    UserRoleDetailsDto getUserRoleDetailsDto(@Param("id") String id);

    @Query("select new springbootpj.clinicpj.dtos.PatientDetailsDto" +
            "(u.userName, u.userBirthDay,u.userGender, u.email, u.userAddress) " +
            "from User as u " +
            "where u.userId = :id")
    PatientDetailsDto findPatientDetailsDto(@Param("id") String id);


}
