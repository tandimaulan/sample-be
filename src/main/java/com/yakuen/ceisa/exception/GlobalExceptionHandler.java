package com.yakuen.ceisa.exception;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<Map<String, String>> handleBusinessException(
    BusinessException exception
  ) {
    return ResponseEntity.status(exception.getStatus()).body(
      Map.of("message", exception.getMessage())
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationException(
    MethodArgumentNotValidException exception
  ) {
    String message = exception
      .getBindingResult()
      .getFieldErrors()
      .stream()
      .map(this::formatFieldError)
      .collect(Collectors.joining(", "));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
      Map.of("message", message)
    );
  }

  private String formatFieldError(FieldError fieldError) {
    return fieldError.getField() + " " + fieldError.getDefaultMessage();
  }
}
