package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public List<Item> getItems(Long userId) {
        log.info("Get items from user {}", userId);
        return itemRepository.findAll(userId);
    }

    @Override
    public Item getItem(Long itemId) {
        log.info("Get item from item {}", itemId);
        return null;
    }

    @Override
    public Item addNewItem(Long userId, Item item) {
        log.info("Add new item from user {}", userId);
        return null;
    }

    @Override
    public Item updateItem(Long itemId, Item item, Long userId) {
        log.info("Update item from user {}", userId);
        return null;
    }
}
