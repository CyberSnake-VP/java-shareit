package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemBookingDateDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    LocalDateTime startBookingDate;
    LocalDateTime endBookingDate;
}
