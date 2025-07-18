package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingItemShort;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingUserShort;
import ru.practicum.shareit.booking.dto.mapper.BookingMapper;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class BookingDtoTest {
    @Autowired
    private JacksonTester<BookingDto> json;

    @Test
    void testSerialize() throws Exception {
        BookingItemShort itemShort = BookingItemShort.builder()
                .id(1L)
                .name("test")
                .build();

        BookingUserShort userShort = BookingUserShort.builder()
                .id(1L)
                .name("test")
                .email("test@test.ru")
                .build();


        BookingDto dto = new BookingDto();
        dto.setId(1L);
        dto.setStart(LocalDateTime.of(2025, 1, 1, 0, 0));
        dto.setEnd(LocalDateTime.of(2025, 1, 2, 0, 0));
        dto.setItem(itemShort);
        dto.setBooker(userShort);
        dto.setStatus(BookingStatus.APPROVED);

        var result = json.write(dto);

        assertThat(result)
                .hasJsonPath("$.id")
                .hasJsonPath("$.start")
                .hasJsonPath("$.end")
                .hasJsonPath("$.item")
                .hasJsonPath("$.booker")
                .hasJsonPath("$.status");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo("2025-01-01T00:00:00");
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo("2025-01-02T00:00:00");
        assertThat(result).extractingJsonPathNumberValue("$.item.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.item.name").isEqualTo(itemShort.getName());
    }

    @Test
    void testMappToBooking() throws Exception {
        BookingItemShort itemShort = BookingItemShort.builder()
                .id(1L)
                .name("test")
                .build();

        BookingUserShort userShort = BookingUserShort.builder()
                .id(1L)
                .name("test")
                .email("test@test.ru")
                .build();

        var dto = new BookingDto();
        dto.setId(1L);
        dto.setStart(LocalDateTime.now());
        dto.setEnd(LocalDateTime.now());
        dto.setBooker(userShort);
        dto.setItem(itemShort);
        dto.setStatus(BookingStatus.WAITING);

        BookingRequestDto requestDto = new BookingRequestDto();
        requestDto.setItemId(1L);
        requestDto.setStart(LocalDateTime.of(2025, 1, 1, 0, 0));
        requestDto.setEnd(LocalDateTime.of(2025, 1, 2, 1, 0));


        var result = BookingMapper.mapToBooking(requestDto, null, null);
        assertThat(Objects.equals(result.getId(), requestDto.getItemId()));
        assertThat(result.getStart().equals(requestDto.getStart()));
    }
}