package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.item.Item;

import java.time.LocalDateTime;

public interface BookingItemDateShort {
    Item getItem();
    LocalDateTime getStartDate();
    LocalDateTime getEndDate();
}
