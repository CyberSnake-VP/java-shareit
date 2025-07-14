package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.*;

import java.util.List;

public interface ItemService {
    List<ItemBookingDateDto> getByUserId(Long userId);

    ItemDto getById(Long itemId);

    ItemDto add(Long userId, ItemRequestDto item);

    ItemDto update(Long itemId, ItemUpdateDto item, Long userId);

    List<ItemDto> search(String textSearch);

    CommentDto addComment(Long userId, Long itemId, CommentDto comment);
}
