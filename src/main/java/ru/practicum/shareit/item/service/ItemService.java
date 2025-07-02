package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> getItems(Long userId);
    Item getItem(Long itemId);
    Item addNewItem(Long userId, Item item);
    Item updateItem(Long itemId, Item item, Long userId);
}
