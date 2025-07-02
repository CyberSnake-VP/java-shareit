package ru.practicum.shareit.item.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@Repository
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {

    // список вещей конкретного пользователя.
    // ключ:id пользователя значение: список его вещей.
    private final Map<Long, List<Item>> items = new HashMap<>();
    private long generateId = 0;

    @Override
    public List<Item> findAll(Long userId) {
        log.info("Find all items by user {}", userId);
        return items.get(userId);
    }

    @Override
    public Optional<Item> findById(Long userId, Long itemId) {
        log.info("Find item by id {}", itemId);
        return items.get(userId).stream()
                .filter(item1 -> item1.getId().equals(itemId))
                .findFirst();
    }

    @Override
    public Item save(Item item) {
        log.info("Save item {}", item);
        if (item.getId() == null) {
            item.setId(++generateId);
        }
        if (items.containsKey(item.getOwner())) {
            items.get(item.getOwner()).add(item);
        } else {
            items.put(item.getOwner(), new ArrayList<>() {{
                    add(item);
                }
            });
        }
        return item;
    }
}
