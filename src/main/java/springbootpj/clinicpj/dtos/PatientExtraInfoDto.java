package springbootpj.clinicpj.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientExtraInfoDto {
    private String userName;
    private String userBirthDay;
    private String userGender;
    private String email;
    private String userAddress;
    private int historyBreath;
    private String moreInfo;
    private LocalDateTime createdAt;
}
