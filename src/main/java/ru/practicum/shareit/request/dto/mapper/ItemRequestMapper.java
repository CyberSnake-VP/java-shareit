package ru.practicum.shareit.request.dto.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestEntryDto;
import ru.practicum.shareit.request.dto.ItemRequestExitDto;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemRequestMapper {

    public static ItemRequest mapToItemRequest(ItemRequestEntryDto requestEntry, Long requestId) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(requestEntry.getDescription());
        itemRequest.setRequestor(requestId);
        return itemRequest;
    }

    public static ItemRequestExitDto mapToItemRequestExitDto(final ItemRequest request, List<ItemDto> items) {
        return new ItemRequestExitDto(
                request.getId(),
                request.getDescription(),
                request.getRequestor(),
                request.getCreated(),
                items
        );
    }
}
