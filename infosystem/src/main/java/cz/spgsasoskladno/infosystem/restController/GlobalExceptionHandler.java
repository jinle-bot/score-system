package cz.spgsasoskladno.infosystem.restController;

import cz.spgsasoskladno.infosystem.exception.BusinessRuleViolation;
import cz.spgsasoskladno.infosystem.exception.NotFoundException;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import cz.spgsasoskladno.infosystem.dto.ErrorResponseDto;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // === 400 BAD REQUEST – error input ===

    @ExceptionHandler(MethodArgumentNotValidException.class) // @Valid na @RequestBody DTO
    public ResponseEntity<ErrorResponseDto> handleValidationErrors(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto("BAD_REQUEST", message));
    }

    @ExceptionHandler(ConstraintViolationException.class) // @RequestParam / @PathVariable @Validated
    public ResponseEntity<ErrorResponseDto> handleConstraintViolations(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .findFirst()
                .orElse("Constraint violation");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto("BAD_REQUEST", message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto("BAD_REQUEST", ex.getMessage()));
    }



    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityExistsException(EntityExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponseDto("UNPROCESSABLE_ENTITY", ex.getMessage()));
    }

    // === 404 NOT FOUND ===
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)  // Set the status code
                .body(new ErrorResponseDto("NOT_FOUND", ex.getMessage()));
    }

    // Business Rule Exception, maybe different status code? CONFLICT?
    @ExceptionHandler(BusinessRuleViolation.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessRuleException(BusinessRuleViolation ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)  // Set the status code
                .body(new ErrorResponseDto("BAD_REQUEST", ex.getMessage()));
    }


    // === 401/403 UNAUTHORIZED / FORBIDDEN ===

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDto("USERNAME_NOT_FOUND", ex.getMessage()));
    }


    @ExceptionHandler(AccessDeniedException.class) // @PreAuthorize failure
    public ResponseEntity<ErrorResponseDto> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponseDto("FORBIDDEN", "Access denied"));
    }

    // === 500 INTERNAL SERVER ERROR ===
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDto(
                        "INTERNAL_SERVER_ERROR",
                        "Something went wrong: " + ex.getMessage()));
    }

}

