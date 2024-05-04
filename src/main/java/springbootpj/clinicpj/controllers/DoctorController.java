package springbootpj.clinicpj.controllers;

import springbootpj.clinicpj.dtos.DoctorExtraInfoDto;
import springbootpj.clinicpj.dtos.GenericResponse;
import springbootpj.clinicpj.dtos.UpdatingScheduleDto;
import springbootpj.clinicpj.exception.UserNotFoundException;
import springbootpj.clinicpj.helpers.Utils;
import springbootpj.clinicpj.services.DoctorService;
import springbootpj.clinicpj.services.JasperReportsService;
import springbootpj.clinicpj.services.PatientService;
import springbootpj.clinicpj.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    private final UserService userService;
    private final DoctorService doctorService;
    private final JasperReportsService jasperReportsService;
    private final PatientService patientService;

    @Autowired
    public DoctorController(UserService userService,
                            DoctorService doctorService,
                            JasperReportsService jasperReportsService,
                            PatientService patientService) {
        this.userService = userService;
        this.doctorService = doctorService;
        this.jasperReportsService = jasperReportsService;
        this.patientService = patientService;
    }

    @GetMapping("/user-information")
    public ResponseEntity<GenericResponse> getPatientDetails(Authentication authentication) {
        List<DoctorExtraInfoDto> doctorExtraInfoDtoList = patientService.getPatientExtraInfoDto(authentication);
        if (!doctorExtraInfoDtoList.isEmpty()) {
            return ResponseEntity.ok(
                    new GenericResponse<>(HttpStatus.OK.value(), doctorExtraInfoDtoList, Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("There is no any relative info.");
    }

    @PutMapping("/new-info-schedule")
    public ResponseEntity<GenericResponse> updateSchedule(@RequestBody UpdatingScheduleDto updatingScheduleDto, Authentication authentication) {
        boolean returnedStatus = doctorService.updateSchedule(updatingScheduleDto, authentication);
        if (returnedStatus && updatingScheduleDto.getIsReservedOrCancellation() == 1) {
            return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(),
                    "Data updated successfully!", Utils.getTimeStampHelper()));
        } else if (returnedStatus && updatingScheduleDto.getIsReservedOrCancellation() == 0) {
            return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(),
                    "The schedule was deleted with the following reason: " + updatingScheduleDto.getReason(),
                    Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("Data not found!");
    }

    @GetMapping("/jasper/patient-extra-info")
    public ResponseEntity<GenericResponse> getPatientDetails(@RequestParam String id) {
        return jasperReportsService.generrateRT(id);
    }
}

