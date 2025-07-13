package ru.practicum.shareit.booking.dto.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingMapper {
  public static BookingDto mapToBookingDto(Booking booking) {
      return new BookingDto(
              booking.getId(),
              booking.getItem().getId(),
              booking.getBooker().getId(),
              booking.getStart(),
              booking.getEnd(),
              booking.getStatus()
      );
  }

  public static Booking mapToBooking(BookingDto bookingDto, Item item, User user) {
      Booking booking = new Booking();
      booking.setStart(bookingDto.getStart());
      booking.setEnd(bookingDto.getEnd());
      booking.setItem(item);
      booking.setBooker(user);
      return booking;
  }

}
