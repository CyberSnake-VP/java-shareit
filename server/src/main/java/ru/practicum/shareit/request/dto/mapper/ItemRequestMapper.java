package ru.practicum.shareit.request.dto.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestEntryDto;
import ru.practicum.shareit.request.dto.ItemRequestExitDto;
import ru.practicum.shareit.request.dto.ItemRequestExitNoItemListDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemRequestMapper {

    public static ItemRequest mapToItemRequest(ItemRequestEntryDto requestEntry, Long requestId) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(requestEntry.getDescription());
        itemRequest.setRequestor(requestId);
        return itemRequest;
    }

    public static ItemRequestExitDto mapToItemRequestExitDto(final ItemRequest request) {
        ItemRequestExitDto itemRequestExitDto = new ItemRequestExitDto();
        itemRequestExitDto.setId(request.getId());
        itemRequestExitDto.setDescription(request.getDescription());
        itemRequestExitDto.setRequestor(request.getRequestor());
        itemRequestExitDto.setCreated(request.getCreated());
        return itemRequestExitDto;
    }

    public static ItemRequestExitNoItemListDto mapToItemRequestExitNoItemListDto(final ItemRequest request) {
        ItemRequestExitNoItemListDto itemRequestExitNoItemListDto = new ItemRequestExitNoItemListDto();
        itemRequestExitNoItemListDto.setId(request.getId());
        itemRequestExitNoItemListDto.setDescription(request.getDescription());
        itemRequestExitNoItemListDto.setRequestor(request.getRequestor());
        itemRequestExitNoItemListDto.setCreated(request.getCreated());
        return itemRequestExitNoItemListDto;
    }
}
