package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;

// Класс для описание поля item в bookingDto
@Data
@Builder
public class BookingItemShort {
    private Long id;
    private String name;
}
