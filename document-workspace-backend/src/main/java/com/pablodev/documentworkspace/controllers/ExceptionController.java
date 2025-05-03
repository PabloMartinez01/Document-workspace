package com.pablodev.documentworkspace.controllers;

import com.pablodev.documentworkspace.constants.ErrorMessages;
import com.pablodev.documentworkspace.dto.error.ErrorResponse;
import com.pablodev.documentworkspace.exceptions.FolderExistsException;
import com.pablodev.documentworkspace.exceptions.UserExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundException(EntityNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE)
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler({FolderExistsException.class, UserExistsException.class})
    public ResponseEntity<ErrorResponse> folderAndUserExistsException(FolderExistsException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> dataAccessException(DataAccessException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ErrorMessages.DATA_ACCESS_MESSAGE)
                .build();
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> badCredentialsException(BadCredentialsException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(ErrorMessages.BAD_CREDENTIALS_MESSAGE)
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ExceptionController::formatFieldErrors)
                .toList();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ErrorMessages.VALIDATION_FAILED_MESSAGE)
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ErrorMessages.INTERNAL_SERVER_ERROR_MESSAGE)
                .build();
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    private static String formatFieldErrors(FieldError error) {
        return String.format("%s: %s", error.getField(), error.getDefaultMessage());
    }

}
