package springbootpj.clinicpj.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClinicSpecializationDto {
    private String clinicName;
    private String clinicAddress;
    private String clinicDescription;
    private String clinicCost;
    private String specializationName;
}
