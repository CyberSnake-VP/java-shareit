package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingState;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {
    BookingDto create(BookingRequestDto booking, Long userId);

    BookingDto updateStatus(Long bookingId, Long ownerId, Boolean approved);

    BookingDto findById(Long bookingId, Long ownerId);

    List<BookingDto> findAllByOwnerId(Long bookerId, BookingState state);

    List<BookingDto> findAllByItemsOwner(Long ownerId, BookingState state);

}
