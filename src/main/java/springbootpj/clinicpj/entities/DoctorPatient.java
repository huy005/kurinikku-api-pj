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
@Table(name = "doctor_patients")
public class DoctorPatient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_patient_id")
    private int doctorPatientId;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;
}
