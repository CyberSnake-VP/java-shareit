package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;
import java.util.List;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<ItemDto> getItems(Long userId) {
        log.info("Get items from user {}", userId);
        return itemRepository.findAll(userId).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto getItem(Long userId, Long itemId) {
        log.info("Get item from item {}", itemId);
        return itemRepository.findById(userId, itemId)
                .map(ItemMapper::toItemDto)
                .orElseThrow(() -> new NotFoundException("Вещь с id " + userId + " не найден."));
    }

    @Override
    public ItemDto addNewItem(Long userId, ItemDto item) {
        log.info("Add new item from user {}", userId);
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден."));
        Item model = ItemMapper.toItem(item);
        model.setOwner(userId);
        return ItemMapper.toItemDto(itemRepository.save(model));
    }

    @Override
    public ItemDto updateItem(Long itemId, ItemDto item, Long userId) {
        log.info("Update item from user {}", userId);
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден."));
        Item model = itemRepository.findById(userId, itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с id " + userId + "не найдена."));

        if (item.getName() != null) {
            model.setName(item.getName());
        }
        if (item.getDescription() != null) {
            model.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            model.setAvailable(item.getAvailable());
        }

        return ItemMapper.toItemDto(itemRepository.save(model));
    }

    @Override
    public List<ItemDto> searchItem(Long userId, String textSearch) {
        log.info("Search item from user {}", userId);
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не существует."));
        List<Item> items = itemRepository.findAll(userId);
        if (items.isEmpty() || textSearch.isEmpty()) {
            return List.of();
        }

        return items.stream()
                .filter(item ->
                        (item.getName().toLowerCase().contains(textSearch.toLowerCase()) ||
                                item.getDescription().toLowerCase().contains(textSearch.toLowerCase())) &&
                                (item.getAvailable() != false))
                .map(ItemMapper::toItemDto)
                .toList();
    }
}
