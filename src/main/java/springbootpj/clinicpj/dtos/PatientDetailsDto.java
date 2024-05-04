package springbootpj.clinicpj.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDetailsDto {
    private String userName;
    private String userBirthDay;
    private String userGender;
    private String email;
    private String userAddress;
}
