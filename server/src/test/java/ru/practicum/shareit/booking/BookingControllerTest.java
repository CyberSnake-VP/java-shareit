package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;

    @Test
    void getAllBookingsByOwner_ReturnBooking() throws Exception {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setStatus(BookingStatus.APPROVED);
        List<BookingDto> requestDookingDto = List.of(bookingDto);
        when(bookingService.findAllByItemsOwner(anyLong(), any())).thenReturn(requestDookingDto);
        mockMvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void getBookings_ReturnBooking() throws Exception {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setStatus(BookingStatus.APPROVED);
        List<BookingDto> requestDookingDto = List.of(bookingDto);
        when(bookingService.findAllByOwnerId(anyLong(), any())).thenReturn(requestDookingDto);
        mockMvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk());
    }


    @Test
    void getBooking_ReturnBooking() throws Exception {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setStatus(BookingStatus.APPROVED);

        when(bookingService.findById(anyLong(), anyLong())).thenReturn(bookingDto);

        mockMvc.perform(get("/bookings/1")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk());
    }


    @Test
    void createBooking_ReturnCreatedBooking() throws Exception {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(2));

        BookingRequestDto savedBooking = new BookingRequestDto();
        savedBooking.setItemId(1L);
        savedBooking.setStart(bookingDto.getStart());
        savedBooking.setEnd(bookingDto.getEnd());

        when(bookingService.create(any(BookingRequestDto.class), anyLong())).thenReturn(bookingDto);

        mockMvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void approveBooking_ReturnUpdatedBooking() throws Exception {
        BookingDto approvedBooking = new BookingDto();
        approvedBooking.setId(1L);
        approvedBooking.setStatus(BookingStatus.APPROVED);

        when(bookingService.updateStatus(anyLong(), anyLong(), anyBoolean())).thenReturn(approvedBooking);

        mockMvc.perform(patch("/bookings/1")
                        .header("X-Sharer-User-Id", 1L)
                        .param("approved", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is("APPROVED")));
    }
}
