package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class BookingServiceImplTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ItemServiceImpl itemService;

    private UserDto owner;
    private UserDto booker;

    private ItemDto itemDto;
    private ItemUpdateDto itemUpdateDto;
    private ItemRequestDto itemDtoCreateRequest;

    private BookingRequestDto bookingDtoCreateRequest;

    @BeforeEach
    void setUp() {
        owner = userService.saveUser(new UserDto(null, "Owner", "owner@mail.com"));
        booker = userService.saveUser(new UserDto(null, "Booker", "booker@mail.com"));

        itemDtoCreateRequest = new ItemRequestDto();
        itemDtoCreateRequest.setName("hammer");
        itemDtoCreateRequest.setDescription("descriptionhammer");
        itemDtoCreateRequest.setAvailable(true);


        itemDto = itemService.add(owner.getId(), itemDtoCreateRequest);

        bookingDtoCreateRequest = new BookingRequestDto();
        bookingDtoCreateRequest.setItemId(itemDto.getId());
        bookingDtoCreateRequest.setStart(LocalDateTime.now().plusDays(1));
        bookingDtoCreateRequest.setEnd(LocalDateTime.now().plusDays(2));
    }


    @Test
    void getBookingById_ReturnBookingForOwnerOrBooker() {
        bookingDtoCreateRequest = new BookingRequestDto();
        bookingDtoCreateRequest.setItemId(itemDto.getId());
        bookingDtoCreateRequest.setStart(LocalDateTime.now());
        bookingDtoCreateRequest.setEnd(LocalDateTime.now());
        BookingDto createdBooking = bookingService.create(bookingDtoCreateRequest, booker.getId());
        BookingDto foundByOwner = bookingService.findById(createdBooking.getId(), owner.getId());
        assertThat(foundByOwner).isNotNull();
        BookingDto foundByBooker = bookingService.findById(createdBooking.getId(), booker.getId());
        assertThat(foundByBooker).isNotNull();
    }

    @Test
    void setApproval_UpdateStatusToApproved() {
        bookingDtoCreateRequest = new BookingRequestDto();
        bookingDtoCreateRequest.setItemId(itemDto.getId());
        bookingDtoCreateRequest.setStart(LocalDateTime.now());
        bookingDtoCreateRequest.setEnd(LocalDateTime.now());
        BookingDto createdBooking = bookingService.create(bookingDtoCreateRequest, booker.getId());
        BookingDto approvedBooking = bookingService.updateStatus(createdBooking.getId(), owner.getId(), true);
        assertThat(approvedBooking.getStatus()).isEqualTo(BookingStatus.APPROVED);
    }


    @Test
    void create_SaveAndReturnBooking() {
        BookingDto createdBooking = bookingService.create(bookingDtoCreateRequest, booker.getId());
        assertThat(createdBooking).isNotNull();
        assertThat(createdBooking.getId()).isNotNull();
        assertThat(createdBooking.getItem().getId()).isEqualTo(itemDto.getId());
        assertThat(createdBooking.getStatus()).isEqualTo(BookingStatus.WAITING);
    }

    @Test
    void create_FailForUnavailableItem() {
        itemUpdateDto = new ItemUpdateDto();
        itemUpdateDto.setAvailable(false);
        itemService.update(itemDto.getId(), itemUpdateDto, owner.getId());
        assertThatThrownBy(() -> bookingService.create(bookingDtoCreateRequest, booker.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void getAllByOwnerAndStateAll_ReturnOwnerBookings() {
        bookingService.create(bookingDtoCreateRequest, booker.getId());
        bookingService.create(bookingDtoCreateRequest, booker.getId());
        List<BookingDto> bookings = bookingService.findAllByItemsOwner(owner.getId(), BookingState.ALL);
        assertThat(bookings).hasSize(2);
    }

    @Test
    void getAllByOwnerAndStatePast_ReturnOwnerBookings() {
        bookingService.create(bookingDtoCreateRequest, booker.getId());
        bookingDtoCreateRequest.setEnd(LocalDateTime.now().minusDays(1));
        bookingService.create(bookingDtoCreateRequest, booker.getId());
        List<BookingDto> bookings = bookingService.findAllByItemsOwner(owner.getId(),
                BookingState.PAST);
        assertThat(bookings).hasSize(1);
    }

    @Test
    void getAllByOwnerAndStateFUTURE_ReturnOwnerBookings() {
        bookingService.create(bookingDtoCreateRequest, booker.getId());
        bookingDtoCreateRequest.setEnd(LocalDateTime.now().plusDays(1));
        bookingService.create(bookingDtoCreateRequest, booker.getId());
        List<BookingDto> bookings = bookingService.findAllByItemsOwner(owner.getId(),
                BookingState.FUTURE);
        assertThat(bookings).hasSize(2);
    }

    @Test
    void getAllByOwnerAndStateCURRENT_ReturnOwnerBookings() {
        bookingService.create(bookingDtoCreateRequest, booker.getId());
        bookingDtoCreateRequest.setEnd(LocalDateTime.now().plusDays(1));
        bookingService.create(bookingDtoCreateRequest, booker.getId());
        List<BookingDto> bookings = bookingService.findAllByOwnerId(owner.getId(),
                BookingState.CURRENT);
        assertThat(bookings).hasSize(0);
    }


    @Test
    void getAllByBookerAndStateAll_ReturnOwnerBookings() {
        bookingService.create(bookingDtoCreateRequest, booker.getId());
        bookingService.create(bookingDtoCreateRequest, booker.getId());
        Collection<BookingDto> bookings = bookingService.findAllByOwnerId(booker.getId(), BookingState.ALL);
        assertThat(bookings).hasSize(2);
    }

    @Test
    void getAllByBookerAndStatePast_ReturnOwnerBookings() {
        bookingService.create(bookingDtoCreateRequest, booker.getId());
        bookingDtoCreateRequest.setEnd(LocalDateTime.now().minusDays(1));
        bookingService.create(bookingDtoCreateRequest, booker.getId());
        Collection<BookingDto> bookings = bookingService.findAllByOwnerId(booker.getId(),
                BookingState.PAST);
        assertThat(bookings).hasSize(1);
    }

    @Test
    void getAllByBookerAndStateFUTURE_ReturnOwnerBookings() {
        bookingService.create(bookingDtoCreateRequest, booker.getId());
        bookingDtoCreateRequest.setEnd(LocalDateTime.now().plusDays(1));
        bookingService.create(bookingDtoCreateRequest, booker.getId());
        Collection<BookingDto> bookings = bookingService.findAllByOwnerId(booker.getId(),
                BookingState.FUTURE);
        assertThat(bookings).hasSize(2);
    }

    @Test
    void getAllByBookerAndStateCURRENT_ReturnOwnerBookings() {
        bookingService.create(bookingDtoCreateRequest, booker.getId());
        bookingDtoCreateRequest.setEnd(LocalDateTime.now().plusDays(1));
        bookingService.create(bookingDtoCreateRequest, booker.getId());
        Collection<BookingDto> bookings = bookingService.findAllByOwnerId(booker.getId(),
                BookingState.CURRENT);
        assertThat(bookings).hasSize(0);
    }

}