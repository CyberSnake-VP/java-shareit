package ru.practicum.shareit.booking;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;


@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    BookingDto create(@RequestHeader("X-Sharer-User-Id") @Positive Long userId,
                      @RequestBody BookingDto booking) {
        return bookingService.create(booking, userId);
    }

    @PatchMapping(value = "/{bookingId}")
    BookingDto updateStatus(@PathVariable("bookingId") Long bookingId,
                            @RequestHeader("X-Sharer-User-Id") @Positive Long ownerId,
                            @RequestParam("approved") Boolean approved) {
        return bookingService.updateStatus(bookingId, ownerId, approved);
    }

    @GetMapping(value = "/{bookingId}")
    BookingDto getBooking(@RequestHeader("X-Sharer-User-Id")@Positive Long ownerId,
                          @PathVariable("bookingId") Long bookingId) {
        return bookingService.getById(bookingId, ownerId);
    }

    @GetMapping
    List<BookingDto> getBookings(@RequestParam(value = "state", required = false, defaultValue = "ALL") BookingState state,
                                 @RequestHeader("X-Sharer-User-Id")@Positive Long bookerId) {
        return bookingService.getAllByBookerId(bookerId, state);
    }

}
