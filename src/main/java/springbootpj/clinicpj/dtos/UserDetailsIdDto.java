package springbootpj.clinicpj.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsIdDto {
    private int userId;
    private String userName;
    private String email;
    private String userBirthDay;
    private String userGender;
    private String userAddress;
    private String userPhoneNumber;
    private String userDescription;
}
