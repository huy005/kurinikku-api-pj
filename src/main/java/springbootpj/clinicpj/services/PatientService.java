package springbootpj.clinicpj.services;

import springbootpj.clinicpj.dtos.DoctorExtraInfoDto;
import springbootpj.clinicpj.dtos.OutstandingClinicDto;
import springbootpj.clinicpj.dtos.OutstandingSpecializationDto;
import springbootpj.clinicpj.dtos.PatientScheduleExtraInfoDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PatientService {
    List<OutstandingSpecializationDto> findSpecializationsBySchedule();

    List<OutstandingClinicDto> findClinicsBySchedule();

    PatientScheduleExtraInfoDto getPatientScheduleExtraInfoDto(String id);

    List<DoctorExtraInfoDto> getPatientExtraInfoDto(Authentication authentication);
}
