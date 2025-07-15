package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingUserShort {
    private Long id;
    private String email;
    private String name;
}
