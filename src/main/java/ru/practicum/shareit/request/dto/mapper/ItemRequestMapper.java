package ru.practicum.shareit.request.dto.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.user.User;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemRequestMapper {

    public static ItemRequest mapToItemRequest(final ItemRequestDto item, User user) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setRequestor(item.getId());
        itemRequest.setDescription(item.getDescription());
        return itemRequest;
    }

    public static ItemRequestResponseDto mapToItemRequestDto(final ItemRequest request, List<ItemDto> items) {
        return new ItemRequestResponseDto(
                request.getId(),
                request.getDescription(),
                request.getRequestor(),
                request.getCreated(),
                items
        );
    }
}
