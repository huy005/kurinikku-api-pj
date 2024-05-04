package springbootpj.clinicpj.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springbootpj.clinicpj.entities.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDetailsDto {
    private String userName;
    private String email;
    private String userBirthDay;
    private String userGender;
    private String userAddress;
    private String userPhoneNumber;
    private String userDescription;
    private Role role;

}
