package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> findAll(Long userId);
    Item findById(Long id);
    Item save(Item item);
}
