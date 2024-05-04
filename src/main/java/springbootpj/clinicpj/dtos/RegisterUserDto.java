package springbootpj.clinicpj.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springbootpj.clinicpj.entities.Role;
import springbootpj.clinicpj.entities.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {
    private String userName;
    private String email;
    private String password;
    private String confirmingPassword;
    private String userBirthDay;
    private String userGender;
    private String userAddress;
    private String userDescription;
    private String userPhoneNumber;
    private int isActive;
    private Role role;
    private String generalIntro;
    private String trainingProcessing;
    private String achievement;
    private String department;
}
