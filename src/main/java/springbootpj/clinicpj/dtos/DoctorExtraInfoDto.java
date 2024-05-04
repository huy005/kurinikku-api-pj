package springbootpj.clinicpj.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorExtraInfoDto {
    private String userName;
    private String email;
    private String userBirthDay;
    private String userGender;
    private String userAddress;
    private int historyBreath;
    private String moreInfo;
}
