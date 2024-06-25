package com.example.WalletApp.CustomAdvice;

import com.example.WalletApp.errors.WalletExceptions;
import com.example.WalletApp.model.WalletSuccessResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ConstraintViolation;

import java.util.List;

@RestControllerAdvice
public class WalletControllerAdvice {

    @ExceptionHandler(WalletExceptions.class)
    public ResponseEntity<WalletSuccessResponse> handleWalletExceptions(WalletExceptions exceptions) {
        List<String> exceptionsMessage = List.of(exceptions.getMessage());
        return new ResponseEntity(new WalletSuccessResponse<>(null, false, exceptionsMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleInternalServerError(Exception ex) {
        return new ResponseEntity("Извините, произошла ошибка на сервере. Пожалуйста, повторите запрос позже.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WalletSuccessResponse> exceptionHandle(ConstraintViolationException exception) {
        var messageSet = exception.getConstraintViolations();
        List<String> errors = messageSet
                .stream()
                .map(ConstraintViolation::getMessage)
                .distinct()
                .toList();
        return new ResponseEntity(new WalletSuccessResponse<>(null, false, errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WalletSuccessResponse> exceptionHandle(MethodArgumentNotValidException exception) {
        var messageSet = exception.getBindingResult().getAllErrors();
        List<String> errors = messageSet
                .stream()
                .map(err -> err.getDefaultMessage())
                .distinct()
                .toList();
        return new ResponseEntity(new WalletSuccessResponse<>(null, false, errors), HttpStatus.BAD_REQUEST);
    }
}
