package RUT.smart_home.exception;

import RUT.smart_home_contract.api.dto.StatusResponse;
import RUT.smart_home_contract.api.exception.ResourceAlreadyExistsException;
import RUT.smart_home_contract.api.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StatusResponse> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new StatusResponse("error", ex.getMessage()));
    }
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<StatusResponse> handleAlreadyExists(ResourceAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new StatusResponse("error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StatusResponse> handleAllExceptions(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusResponse("error", "Внутренняя ошибка: " + ex.getMessage()));
    }
}