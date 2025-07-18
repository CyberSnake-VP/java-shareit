package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookItemRequestDto {
    @NotNull(message = "itemId не может быть null")
    @Positive(message = "itemId должен быть положительным числом")
    private Long itemId;

    @NotNull(message = "начало аренды не может быть null")
    @FutureOrPresent
    private LocalDateTime start;

    @NotNull(message = "окончание аренды не может быть null")
    @Future
    private LocalDateTime end;
}