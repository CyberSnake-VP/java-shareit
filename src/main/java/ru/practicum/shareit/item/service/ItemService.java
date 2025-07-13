package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    List<ItemDto> getByUserId(Long userId);

    ItemDto getById(Long itemId);

    ItemDto add(Long userId, ItemDto item);

    ItemDto update(Long itemId, ItemDto item, Long userId);

    List<ItemDto> search(String textSearch);
}
