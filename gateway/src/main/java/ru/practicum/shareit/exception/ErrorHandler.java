package ru.practicum.shareit.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.error("Ошибка при валидации: {}", ex.getMessage());
        return ErrorResponse.builder().error(ex.getMessage()).build();
    }

    // В случаях где нет @Valid Hibernate вызовет исключение на уровне JPA и сгенерирует ConstraintViolationException.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse handleConstraintViolation(final ConstraintViolationException e) {
        log.info("Constraint violation: {}", e.getMessage());
        log.warn(e.getMessage());
        return ErrorResponse.builder().error(e.getMessage()).build();
    }
}
