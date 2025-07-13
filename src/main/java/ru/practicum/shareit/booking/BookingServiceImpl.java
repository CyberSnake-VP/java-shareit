package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.mapper.BookingMapper;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public BookingDto create(BookingDto bookingDto, Long userId) {
        log.info("Create booking: {}", bookingDto);
        Item item = itemRepository.findByIdAndAvailableIsTrue(bookingDto.getItemId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item not found or not available"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Booking booking = BookingMapper.mapToBooking(bookingDto, item, user);
        booking.setStatus(BookingStatus.WAITING);
        return BookingMapper.mapToBookingDto(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    public BookingDto updateStatus(Long bookingId, Long ownerId, Boolean approved) {
        log.info("Update booking: {}", bookingId);
        // запросный метод, проверяем бронирование и владельца вещи указанной в бронировании.
        Booking booking = bookingRepository.findByIdAndItem_Owner_Id(bookingId, ownerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking not found or not available"));

        // устанавливаем статус в зависимости от значения переменной принятой в контроллере.
        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
            return BookingMapper.mapToBookingDto(bookingRepository.save(booking));
        }
        booking.setStatus(BookingStatus.REJECTED);
        return BookingMapper.mapToBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto getById(Long bookingId, Long ownerId) {
        log.info("Get booking: {}", bookingId);
        //запросный метод, проверяем владельца вещи, либо автора бронирования.
        Booking booking = bookingRepository.findBookingByItemOwnerOrBookingOwner(bookingId, ownerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking not found or not available"));

        return BookingMapper.mapToBookingDto(booking);
    }

    @Override
    public List<BookingDto> getAllByBookerId(Long bookerId, BookingState state) {

        List<Booking> bookings;
        LocalDateTime currentDate = LocalDateTime.now();
        switch (state) {
            case ALL -> bookings = bookingRepository.findAllByBooker_IdOrderByStartDesc(bookerId);
            case CURRENT -> bookings =bookingRepository.findAllByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc(bookerId, currentDate, currentDate);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The booking state is incorrect.: " + state);
        }


        return bookings.stream().map(BookingMapper::mapToBookingDto).toList();
    }
}
