package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingRequestDto {
    private Long itemId;
    @FutureOrPresent(message = "Укажите текущее или будущее время для бронирования.")
    @NotNull
    private LocalDateTime start;
    @Future(message = "Окончание бронирования не может быть в раньше текущей даты.")
    @NotNull
    private LocalDateTime end;
}
