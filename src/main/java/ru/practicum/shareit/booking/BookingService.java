package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {
    BookingDto create(BookingDto booking, Long userId);
    BookingDto updateStatus(Long bookingId, Long ownerId, Boolean approved);
    BookingDto getById(Long bookingId, Long ownerId);
    List<BookingDto> getAllByBookerId(Long bookerId, BookingState state);
}
