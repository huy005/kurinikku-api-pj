package springbootpj.clinicpj.controllers;


import springbootpj.clinicpj.dtos.*;
import springbootpj.clinicpj.entities.User;
import springbootpj.clinicpj.exception.UserNotFoundException;
import springbootpj.clinicpj.helpers.Utils;
import springbootpj.clinicpj.services.PatientService;
import springbootpj.clinicpj.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final UserService userService;
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService,
                             UserService userService) {
        this.patientService = patientService;
        this.userService = userService;
    }


    @GetMapping("/specializations/top")
    public ResponseEntity<GenericResponse> getSpecializations() {
        List<OutstandingSpecializationDto> outstandingSpecializations = patientService.findSpecializationsBySchedule();
        if (outstandingSpecializations != null) {
            return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(),
                    outstandingSpecializations, Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("There is no any outstanding specializations.");
    }

    @GetMapping("/clinics/top")
    public ResponseEntity<GenericResponse> getClinics() {
        List<OutstandingClinicDto> outstandingClinics = patientService.findClinicsBySchedule();
        if (outstandingClinics != null) {
            return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(),
                    outstandingClinics, Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("There is no any outstanding clinics.");
    }

    //    5.1.6. Hiển thị thông tin cá nhân:
    @GetMapping("/personal-info")
    public ResponseEntity<GenericResponse> getUserDetails(Authentication authentication) {
        UserScheduleDto userScheduleDto = userService.findUserScheduleDto(authentication);
        if (userScheduleDto != null) {
            return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(), userScheduleDto, Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("The user not found.");
    }

    //    5.1.7
    @GetMapping("/clinic-information")
    public ResponseEntity<GenericResponse> findClinicWithKeywords(@RequestBody ClinicSpecializationDto clinicSpecializationDto) {
        List<ClinicSpecializationDto> clinicDtos = userService.findClinicWithKeywords(clinicSpecializationDto);
        if (clinicDtos != null) {
            return ResponseEntity.ok(new GenericResponse<>(HttpStatus.OK.value(), clinicDtos, Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("There is no any matched object.");
    }

    //    5.1.8
    @GetMapping("/doctor-information")
    public ResponseEntity<GenericResponse> getDoctorDetailsSpecialization(@RequestParam String id) {
        List<DoctorInformationDto> doctorInformation2Dto = userService.findDoctorInformationDto(id);
        if (doctorInformation2Dto != null) {
            return ResponseEntity.ok(new GenericResponse<>(HttpStatus.OK.value(), doctorInformation2Dto, Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("There is no any matched object.");
    }
//    5.1.9
    @GetMapping("/new-schedule")
    public ResponseEntity<GenericResponse> updateDoctorDetailsSchedule(@RequestBody DoctorScheduleDetailsDto doctorScheduleDetailsDto) {
        User doctor = userService.findById(doctorScheduleDetailsDto.getUserId());
        if (doctor != null) {
            userService.updateDoctorDetails(doctorScheduleDetailsDto, doctor);
            return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(),
                    "A new schedule updated successfully!", Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("Failed to update the information!");
    }

}





