package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.dto.ItemRequestEntryDto;
import ru.practicum.shareit.request.dto.ItemRequestExitDto;
import ru.practicum.shareit.request.dto.ItemRequestExitNoItemListDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class ItemRequestServiceImplTest {
    @Autowired
    private ItemRequestServiceImpl itemRequestService;

    @Autowired
    private UserServiceImpl userService;

    private UserDto owner;
    private UserDto user;
    private ItemDto itemDto;
    private ItemRequestEntryDto itemRequestrDtoCreateRequest;

    @BeforeEach
    void setUp() {
        owner = userService.saveUser(new UserDto(null, "owner", "owner@owner.com"));
        user = userService.saveUser(new UserDto(null, "user", "user@user.com"));


        itemDto = new ItemDto();
        itemDto.setName("hammer");
        itemDto.setDescription("descriptionhammer");
        itemDto.setAvailable(true);


        itemRequestrDtoCreateRequest = new ItemRequestEntryDto();
        itemRequestrDtoCreateRequest.setDescription("descriptionhammer");

    }

    @Test
    void addItemRequest_SaveAndReturnItem() {
        ItemRequestExitNoItemListDto createdItem = itemRequestService.create(user.getId(), itemRequestrDtoCreateRequest);
        assertThat(createdItem).isNotNull();
    }

    @Test
    void addItemRequestErrorUser_SaveAndReturnItem() {
        assertThatThrownBy(() -> itemRequestService.create(777L, itemRequestrDtoCreateRequest))
                .isInstanceOf(NotFoundException.class);
    }


    @Test
    void getAllItemRequests_ReturnAllItems() {
        itemRequestrDtoCreateRequest = new ItemRequestEntryDto();
        itemRequestrDtoCreateRequest.setDescription("descriptionhammer");
        ItemRequestEntryDto itemRequestrDtoCreateRequest2 = new ItemRequestEntryDto();
        itemRequestrDtoCreateRequest2.setDescription("descriptionhammer2");
        itemRequestService.create(owner.getId(), itemRequestrDtoCreateRequest);
        itemRequestService.create(user.getId(), itemRequestrDtoCreateRequest2);
        List<ItemRequestExitDto> items = itemRequestService.getAll().stream().toList();
        assertThat(items).hasSize(2);
    }

    @Test
    void getAllItemRequestsByRequestor_ReturnAllItems() {
        itemRequestrDtoCreateRequest = new ItemRequestEntryDto();
        itemRequestrDtoCreateRequest.setDescription("descriptionhammer");
        ItemRequestEntryDto itemRequestrDtoCreateRequest2 = new ItemRequestEntryDto();
        itemRequestrDtoCreateRequest2.setDescription("descriptionhammer2");
        itemRequestService.create(owner.getId(), itemRequestrDtoCreateRequest);
        itemRequestService.create(user.getId(), itemRequestrDtoCreateRequest2);
        List<ItemRequestExitDto> items = itemRequestService.getByRequestorId(user.getId()).stream().toList();
        assertThat(items).hasSize(1);
    }

    @Test
    void getItemRequestById_ReturnExistingItem() {
        itemRequestrDtoCreateRequest = new ItemRequestEntryDto();
        itemRequestrDtoCreateRequest.setDescription("descriptionhammer");
        ItemRequestExitNoItemListDto itemRequestDto = itemRequestService.create(owner.getId(), itemRequestrDtoCreateRequest);

        ItemRequestExitDto foundItem = itemRequestService.getById(itemRequestDto.getId());
        assertThat(foundItem).isNotNull();
        assertThat(foundItem.getId()).isEqualTo(itemRequestDto.getId());
        assertThat(foundItem.getDescription()).isEqualTo(itemRequestDto.getDescription());
    }
}