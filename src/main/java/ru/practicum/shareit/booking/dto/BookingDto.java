package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.BookingStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private BookingItemShort item;
    private BookingUserShort booker;
    private LocalDateTime start;
    private LocalDateTime end;
    private BookingStatus status;
}
