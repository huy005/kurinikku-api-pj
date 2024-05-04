package springbootpj.clinicpj.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorInformationDto {
    private String userName;
    private String email;
    private String userAddress;
    private String userPhoneNumber;
    private String specializationName;
    private String specializationDescription;
}
