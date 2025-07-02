package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    List<ItemDto> getItems(Long userId);

    ItemDto getItem(Long userId, Long itemId);

    ItemDto addNewItem(Long userId, ItemDto item);

    ItemDto updateItem(Long itemId, ItemDto item, Long userId);
}
