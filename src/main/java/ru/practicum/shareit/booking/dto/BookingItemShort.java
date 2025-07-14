package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;

// Классы
@Data
@Builder
public class BookingItemShort {
    private Long id;
    private String name;
}
