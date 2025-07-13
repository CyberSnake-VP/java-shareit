package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDate;
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

    // Время старта бронирования раньше, чем текущее время и заканчивается дольше, чем текущее.
    List<Booking> findAllByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc(Long bookerId, LocalDateTime currentDate, LocalDateTime currentDate1);
}
