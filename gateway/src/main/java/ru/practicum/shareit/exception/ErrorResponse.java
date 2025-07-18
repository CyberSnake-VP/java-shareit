package ru.practicum.shareit.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String error;
}
