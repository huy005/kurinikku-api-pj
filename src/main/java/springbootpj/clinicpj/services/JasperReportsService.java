package springbootpj.clinicpj.services;

import springbootpj.clinicpj.dtos.GenericResponse;
import org.springframework.http.ResponseEntity;

public interface JasperReportsService {
    ResponseEntity<GenericResponse> generrateRT(String id);
}
