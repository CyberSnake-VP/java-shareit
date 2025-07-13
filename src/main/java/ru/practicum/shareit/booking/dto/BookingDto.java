package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.BookingStatus;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDto {
    @Positive(message = "Поле id должно быть положительным числом.")
    private Long id;
    @NotBlank(message = "Поле itemId должно быть заполнено.")
    private Long ItemId;
    private Long bookerId;
    @FutureOrPresent(message = "Укажите текущее или будущее время для бронирования.")
    private LocalDateTime start;
    @Future(message = "Окончание бронирования не может быть в раньше текущей даты.")
    private LocalDateTime end;
    private BookingStatus status;
}
