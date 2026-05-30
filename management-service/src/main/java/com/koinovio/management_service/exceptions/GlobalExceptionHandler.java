package com.koinovio.management_service.exceptions;

import com.koinovio.management_service.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(TenantAlreadyInactiveException.class)
    public ResponseEntity<ErrorResponse> handleTenantIsAlreadyInactive(TenantAlreadyInactiveException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(ApartmentAlreadyOccupiedException.class)
    public ResponseEntity<ErrorResponse> handleApartmentIsOccupied(ApartmentAlreadyOccupiedException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJson(HttpMessageNotReadableException ex) {
        String message = "Invalid JSON input: " + ex.getMostSpecificCause().getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Map<String,String>>> handleValidationErrors(MethodArgumentNotValidException ex){

        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage(),
                        (existing, replacement) -> existing // in case of duplicate fields
                ));

        Map<String,Map<String,String>> response = Map.of("message",fieldErrors);

        return ResponseEntity.badRequest().body(response);


    }

    /*
    Handles argument mismatch, e.g. when giving abc as id but expecting a number
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid type for parameter '" + ex.getName() +
                "'. Expected a positive number but received: " + ex.getValue();

        return ResponseEntity.badRequest().body(new ErrorResponse(message));
    }


    /*
    Handles validation errors for the PathVariable , e.g. when giving a negative number as id
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.joining("\n"));

        return ResponseEntity.badRequest().body(new ErrorResponse(message));
    }
}
