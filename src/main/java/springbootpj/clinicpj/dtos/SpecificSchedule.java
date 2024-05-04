package springbootpj.clinicpj.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springbootpj.clinicpj.entities.User;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecificSchedule {
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private User doctor;
}
