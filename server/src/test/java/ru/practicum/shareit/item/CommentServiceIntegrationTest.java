package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class CommentServiceIntegrationTest {


    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private BookingServiceImpl bookingService;

    private UserDto owner;
    private UserDto booker;
    private ItemDto itemDto;
    private ItemRequestDto itemDtoCreateRequest;
    private BookingDto bookingDto;
    private CommentDto commentDto;

    @BeforeEach
    void setUp() {

        owner = userService.saveUser(new UserDto(null, "owner", "owner@owner.com"));
        booker = userService.saveUser(new UserDto(null, "user", "user@user.com"));


        itemDtoCreateRequest = new ItemRequestDto();
        itemDtoCreateRequest.setName("hammer");
        itemDtoCreateRequest.setDescription("descriptionhammer");
        itemDtoCreateRequest.setAvailable(true);

        itemDto = itemService.add(owner.getId(), itemDtoCreateRequest);

        BookingRequestDto bookingRequestDto = new BookingRequestDto();
        bookingRequestDto.setItemId(itemDto.getId());
        bookingRequestDto.setStart(LocalDateTime.now().minusDays(5));
        bookingRequestDto.setEnd(LocalDateTime.now().minusDays(2));

        bookingDto = bookingService.create(bookingRequestDto, booker.getId());

        //обновляем статус
        bookingService.updateStatus(owner.getId(), bookingDto.getId(), true);

        commentDto = new CommentDto(0L, "cometntext", "autorname", LocalDateTime.now());

    }

    @Test
    void addComment_SaveAndReturnComment() {
        CommentDto createdComment = itemService.addComment(booker.getId(), itemDto.getId(), commentDto);
        assertThat(createdComment).isNotNull();
        assertThat(createdComment.getText()).isEqualTo("cometntext");
        assertThat(createdComment.getAuthorName()).isEqualTo(booker.getName());
        assertThat(createdComment.getCreated()).isNotNull();
    }

}