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
public class DoctorScheduleDto {
    private DoctorDetailsDto doctorDetailsDto;
    private List<ScheduleDetailsDto> scheduleDetailsDtoList;
}
