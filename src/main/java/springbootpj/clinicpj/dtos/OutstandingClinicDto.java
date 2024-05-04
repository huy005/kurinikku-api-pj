package springbootpj.clinicpj.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OutstandingClinicDto {
    private int clinicId;
    private String clinicName;
    private String clinicAddress;
    private String clinicDescription;
    private Long total;

    public OutstandingClinicDto(int clinicId, String clinicName, String clinicAddress, String clinicDescription, Long total) {
        this.clinicId = clinicId;
        this.clinicName = clinicName;
        this.clinicAddress = clinicAddress;
        this.clinicDescription = clinicDescription;
        this.total = total;
    }
}
