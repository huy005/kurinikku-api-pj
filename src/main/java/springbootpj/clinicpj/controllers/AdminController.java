package springbootpj.clinicpj.controllers;

import springbootpj.clinicpj.dtos.*;
import springbootpj.clinicpj.entities.User;
import springbootpj.clinicpj.exception.UserNotFoundException;
import springbootpj.clinicpj.helpers.Utils;
import springbootpj.clinicpj.services.AuthenticationService;
import springbootpj.clinicpj.services.DoctorService;
import springbootpj.clinicpj.services.PatientService;
import springbootpj.clinicpj.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @Autowired
    public AdminController(UserService userService,
                           AuthenticationService authenticationService,
                           DoctorService doctorService,
                           PatientService patientService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @PutMapping("/status-activation")
    public ResponseEntity<GenericResponse> lockOrUnlock(@RequestBody ActiveUser activeUser) {
        boolean statusActivation = userService.lockOrUnlock(activeUser);
        if (statusActivation && activeUser.getIsActive() == 1) {
            return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(),
                    "This user is unlocked", Utils.getTimeStampHelper()));
        } else if (statusActivation && activeUser.getIsActive() == 0) {
            return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(),
                    "This user is locked with the following reason: " + activeUser.getReason(), Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("The input value of isActive is invalid! " +
                "or Could not find this user with " + activeUser.getEmail());

    }

    @PostMapping("/new-user")
    public ResponseEntity<GenericResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        User user = userService.findByEmail(registerUserDto.getEmail());
        if (user == null) {
            User registeredUser = authenticationService.signup(registerUserDto);
            if (registeredUser != null) {
                return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(),
                        "A new user registered.", Utils.getTimeStampHelper()));
            } else {
                throw new UserNotFoundException("The input password is invalid");
            }
        } else {
            throw new UserNotFoundException("User has registered already - " + registerUserDto.getEmail());
        }
    }

    @GetMapping("/doctor-schedule-details")
    public ResponseEntity<DoctorScheduleDto> getDoctorScheduleDetails(@RequestParam String id) {
        return ResponseEntity.ok(doctorService.getDoctorSchedules(id));
    }

    @GetMapping("/patient-schedule-extraInfo-details")
    public ResponseEntity<GenericResponse> getPatientScheduleExtraInfoDto(@RequestParam String id) {
        PatientScheduleExtraInfoDto patientScheduleExtraInfoDto = patientService.getPatientScheduleExtraInfoDto(id);
        if (patientScheduleExtraInfoDto != null) {
            return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(),
                    patientScheduleExtraInfoDto, Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("Data not found.");
    }
}
