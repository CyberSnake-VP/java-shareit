package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;


@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    BookingDto create(@RequestHeader("X-Sharer-User-Id") @Positive Long userId,
                      @RequestBody @Valid BookingRequestDto booking) {
        return bookingService.create(booking, userId);
    }

    @PatchMapping(value = "/{bookingId}")
    BookingDto updateStatus(@PathVariable("bookingId") @Positive Long bookingId,
                            @RequestHeader("X-Sharer-User-Id") @Positive Long ownerId,
                            @RequestParam("approved") Boolean approved) {
        return bookingService.updateStatus(bookingId, ownerId, approved);
    }

    @GetMapping(value = "/{bookingId}")
    BookingDto getBooking(@RequestHeader("X-Sharer-User-Id") @Positive Long ownerId,
                          @PathVariable("bookingId") @Positive Long bookingId) {
        return bookingService.findById(bookingId, ownerId);
    }

    @GetMapping
    List<BookingDto> getAllBookingsByState(@RequestParam(value = "state", required = false, defaultValue = "ALL") BookingState state,
                                           @RequestHeader("X-Sharer-User-Id") @Positive Long bookerId) {
        return bookingService.findAllByOwnerId(bookerId, state);
    }

    @GetMapping(path = "/owner")
    List<BookingDto> getAllBookingsByItems(@RequestParam(value = "state", required = false, defaultValue = "ALL") BookingState state,
                                           @RequestHeader("X-Sharer-User-Id") @Positive Long ownerId) {
        return bookingService.findAllByItemsOwner(ownerId, state);
    }

}
