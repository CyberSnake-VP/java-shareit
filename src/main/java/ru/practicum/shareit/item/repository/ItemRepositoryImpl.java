package ru.practicum.shareit.item.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {

    // список вещей конкретного пользователя.
    private final Map<Long, List<Item>> items = new HashMap<>();

    @Override
    public List<Item> findAll(Long userId) {
        log.debug("Find all items by user {}", userId);
        return List.of();
    }

    @Override
    public Item findById(Long id) {
        log.debug("Find item by id {}", id);
        return null;
    }

    @Override
    public Item save(Item item) {
        log.debug("Save item {}", item);
        return null;
    }
}
