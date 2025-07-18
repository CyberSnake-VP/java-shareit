package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemBookingDateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class ItemServiceImplTest {

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private UserServiceImpl userService;

    private UserDto owner;
    private UserDto user;
    private ItemRequestDto itemDtoCreateRequest;

    @BeforeEach
    void setUp() {
        owner = userService.saveUser(new UserDto(null, "owner", "owner@owner.com"));
        user = userService.saveUser(new UserDto(null, "user", "user@user.com"));

        ItemDto itemDto = new ItemDto();
        itemDto.setName("hammer");
        itemDto.setDescription("descriptionhammer");
        itemDto.setAvailable(true);

        itemDtoCreateRequest = new ItemRequestDto();
        itemDtoCreateRequest.setName("hammer");
        itemDtoCreateRequest.setDescription("descriptionhammer");
        itemDtoCreateRequest.setAvailable(true);
    }

    @Test
    void searchItems_ReturnMatchingItems() {
        ItemRequestDto item1 = new ItemRequestDto();
        item1.setName("hammer");
        item1.setDescription("descriptionhammer");
        item1.setAvailable(true);
        itemService.add(owner.getId(), item1);
        ItemRequestDto item2 = new ItemRequestDto();
        item2.setName("remmer");
        item2.setDescription("gg remmer");
        item2.setAvailable(true);
        itemService.add(owner.getId(), item2);
        List<ItemDto> results = itemService.search("hammer").stream().toList();
        assertThat(results).hasSize(1);
        assertThat(results.getFirst().getName()).isEqualTo("hammer");
    }

    @Test
    void searchItemsError_ReturnMatchingItems() {
        ItemRequestDto item1 = new ItemRequestDto();
        item1.setName("hammer");
        item1.setDescription("descriptionhammer");
        item1.setAvailable(true);
        itemService.add(owner.getId(), item1);
        ItemRequestDto item2 = new ItemRequestDto();
        item2.setName("remmer");
        item2.setDescription("gg remmer");
        item2.setAvailable(true);
        itemService.add(owner.getId(), item2);
        List<ItemDto> results = itemService.search("").stream().toList();
        assertThat(results).hasSize(0);

    }

    @Test
    void updateItem_UpdateItemDetails() {
        ItemDto createdItem = itemService.add(owner.getId(), itemDtoCreateRequest);
        ItemUpdateDto updateDto = new ItemUpdateDto();
        updateDto.setName("new hammer");
        updateDto.setDescription("new description hammer");
        updateDto.setAvailable(false);
        ItemDto updatedItem = itemService.update(createdItem.getId(), updateDto, owner.getId());
        assertThat(updatedItem.getId()).isEqualTo(createdItem.getId());
        assertThat(updatedItem.getName()).isEqualTo("new hammer");
        assertThat(updatedItem.getDescription()).isEqualTo("new description hammer");
        assertThat(updatedItem.getAvailable()).isFalse();
    }

    @Test
    void getItem_ThrowExceptionIfItemNotFound() {
        assertThatThrownBy(() -> itemService.getById(999L))
                .isInstanceOf(NotFoundException.class);
    }


    @Test
    void getUserItems_ReturnAllItemsForOwner() {
        ItemRequestDto item1 = new ItemRequestDto();
        item1.setName("hammer");
        item1.setDescription("descriptionhammer");
        item1.setAvailable(true);
        itemService.add(owner.getId(), item1);
        ItemRequestDto item2 = new ItemRequestDto();
        item2.setName("Hammer");
        item2.setDescription("2 hammer");
        item2.setAvailable(true);
        itemService.add(user.getId(), item2);
        List<ItemBookingDateDto> items = itemService.getAllByUser(owner.getId()).stream().toList();
        assertThat(items).hasSize(1);
    }

    @Test
    void addItem_SaveAndReturnItem() {
        ItemDto createdItem = itemService.add(owner.getId(), itemDtoCreateRequest);
        assertThat(createdItem).isNotNull();
        assertThat(createdItem.getId()).isNotNull();
        assertThat(createdItem.getName()).isEqualTo(itemDtoCreateRequest.getName());
        assertThat(createdItem.getDescription()).isEqualTo(itemDtoCreateRequest.getDescription());
        assertThat(createdItem.getAvailable()).isTrue();
    }


    @Test
    void addItemErrorRequest_SaveAndReturnItem() {
        itemDtoCreateRequest.setRequestId(5555L);
        assertThatThrownBy(() -> itemService.add(0L, itemDtoCreateRequest))
                .isInstanceOf(NotFoundException.class);
    }


    @Test
    void getItem_ReturnExistingItem() {
        ItemDto createdItem = itemService.add(owner.getId(), itemDtoCreateRequest);
        ItemBookingDateDto foundItem = itemService.getById(createdItem.getId());
        assertThat(foundItem).isNotNull();
        assertThat(foundItem.getId()).isEqualTo(createdItem.getId());
        assertThat(foundItem.getName()).isEqualTo(createdItem.getName());
    }

    @Test
    void searchItems_ReturnEmptyListIfNoMatch() {
        itemService.add(owner.getId(), itemDtoCreateRequest);
        List<ItemDto> results = itemService.search("dfgdsfgsdfhgsdh").stream().toList();
        assertThat(results).isEmpty();
    }

}