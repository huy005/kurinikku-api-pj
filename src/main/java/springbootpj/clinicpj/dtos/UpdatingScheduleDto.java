package springbootpj.clinicpj.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatingScheduleDto {
    private int isReservedOrCancellation;
    private int scheduleId;
    private String reason;
}
