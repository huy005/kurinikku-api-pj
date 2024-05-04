package springbootpj.clinicpj.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientScheduleExtraInfoDto {
    private PatientDetailsDto patientDetailsDto;
    private List<ScheduleDetailsDto> scheduleDetailsDtoList;
    private List<ExtraInfoDto> extraInfoRepository;
}
