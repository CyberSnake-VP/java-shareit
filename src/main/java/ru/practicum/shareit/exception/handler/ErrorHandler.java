package ru.practicum.shareit.exception.handler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleValidate(final ValidationException e) {
        log.warn(e.getMessage());
        return ErrorResponse.builder().error(e.getMessage()).build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleError(final RuntimeException e) {
        log.warn(e.getMessage());
        return ErrorResponse.builder().error(e.getMessage()).build();
    }

    // Spring на уровне контроллеров @Valid выбрасывает MethodArgumentNotValidException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse handleArgumentNotValid(final MethodArgumentNotValidException e) {
        log.info("MethodArgumentNotValidException: {}", e.getMessage());
        log.warn(Objects.requireNonNull(e.getFieldError()).getDefaultMessage());
        return ErrorResponse.builder().error(e.getFieldError().getDefaultMessage()).build();
    }

    // В случаях где нет @Valid Hibernate вызовет исключение на уровне JPA и сгенерирует ConstraintViolationException.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse handleConstraintViolation(final ConstraintViolationException e) {
        log.info("Constraint violation: {}", e.getMessage());
        log.warn(e.getMessage());
        return ErrorResponse.builder().error(e.getMessage()).build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ErrorResponse handleNotFound(final NotFoundException e) {
        log.warn(e.getMessage());
        return ErrorResponse.builder().error(e.getMessage()).build();
    }
}
