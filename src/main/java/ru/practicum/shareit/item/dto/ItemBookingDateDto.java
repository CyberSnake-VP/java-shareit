package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ItemBookingDateDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    LocalDateTime startBookingDate;
    LocalDateTime endBookingDate;
}
