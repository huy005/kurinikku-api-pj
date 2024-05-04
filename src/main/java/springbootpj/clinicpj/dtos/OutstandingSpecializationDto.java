package springbootpj.clinicpj.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OutstandingSpecializationDto {
    private int specializationId;
    private String specializationName;
    private String specializationDescription;
    private Long total;
}
