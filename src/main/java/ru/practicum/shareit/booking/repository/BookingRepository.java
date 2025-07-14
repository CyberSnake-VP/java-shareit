package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByIdAndItem_Owner_Id(Long bookingId, Long ownerId);

    @Query("select b from Booking b" +
            " where b.id = ?1" +
            " and b.item.owner.id = ?2 " +
            "or b.booker.id = ?2")
    Optional<Booking> findBookingByItemOwnerOrBookingOwner(Long bookingId, Long ownerId);

    List<Booking> findAllByBooker_IdOrderByStartDesc(Long bookerId);

    /**
     * Проверка по state, получение списка всех бронирований пользователя
     */
    // Время старта бронирования раньше, чем текущее время и заканчивается дольше, чем текущее.
    List<Booking> findAllByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc(Long bookerId, LocalDateTime currentDate, LocalDateTime currentDate1);

    // Время окончания бронирования раньше текущего времени.
    List<Booking> findAllByBooker_IdAndEndBeforeOrderByStartDesc(Long bookerId, LocalDateTime currentDate);

    // Время начала бронирования позже текущего времени.
    List<Booking> findAllByBooker_IdAndStartAfterOrderByStartDesc(Long bookerId, LocalDateTime currentDate);

    // Проверяем статус бронирования
    List<Booking> findAllByBooker_IdAndStatusOrderByStartDesc(Long bookerId, BookingStatus bookingStatus);

    /**
     * Проверка по state, получение списка бронирования вещей у владельца
     */
    List<Booking> findAllByItem_Owner_IdOrderByStartDesc(Long ownerId);

    List<Booking> findAllByItem_Owner_IdAndStartBeforeAndEndAfterOrderByStartDesc(Long ownerId, LocalDateTime currentDate, LocalDateTime currentDate1);

    List<Booking> findAllByItem_Owner_IdAndEndBeforeOrderByStartDesc(Long ownerId, LocalDateTime currentDate);

    List<Booking> findAllByItem_Owner_IdAndStartAfterOrderByStartDesc(Long ownerId, LocalDateTime currentDate);

    List<Booking> findAllByItem_Owner_IdAndStatusOrderByStartDesc(Long ownerId, BookingStatus bookingStatus);

}
