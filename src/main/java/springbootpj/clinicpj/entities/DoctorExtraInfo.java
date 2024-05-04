package springbootpj.clinicpj.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctor_extra_info")
public class DoctorExtraInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_extra_info_id")
    private int doctorExtraInfoId;

    @Column(name = "general_intro")
    private String generalIntro;

    @Column(name = "training_processing")
    private String trainingProcessing;

    @Column(name = "achievement")
    private String achievement;

    @Column(name = "department")
    private String department;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "doctor_id")
    private User doctor;
}
