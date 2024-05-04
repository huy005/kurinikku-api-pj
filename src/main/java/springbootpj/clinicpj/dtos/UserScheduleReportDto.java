package springbootpj.clinicpj.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserScheduleReportDto {
    private String userName;
    private String userBirthDay;
    private String email;
    private String scheduleDate;
    private String scheduleTime;
}
