package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemBookingDateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;

    /**
     * В этом методе получаем объект бронирования, после чего маппим его в нужный нам объект dto получая значение
     * полей startDate и endDate. Использовал мапу, чтобы получить id item'а в качестве ключа.
     */
    @Override
    public List<ItemBookingDateDto> getByUserId(Long userId) {
        log.info("Get items from user {}", userId);

        Map<Long, Item> items = itemRepository.findByOwner_Id(userId).stream()
                .collect(Collectors.toMap(Item::getId, Function.identity()));
        Map<Long, Booking> bookings = bookingRepository.findAllByItem_Owner_IdOrderByStartDesc(userId).stream()
                .collect(Collectors.toMap(booking -> booking.getItem().getId(), Function.identity()));
        ;
        return ItemMapper.mapToItemBookingDateDto(bookings, items);
    }

    @Override
    public ItemDto getById(Long itemId) {
        log.info("Get item from item {}", itemId);
        return itemRepository.findById(itemId)
                .map(ItemMapper::toItemDto)
                .orElseThrow(() -> new NotFoundException("Вещь с id " + itemId + " не найден."));
    }

    @Override
    @Transactional
    public ItemDto add(Long userId, ItemRequestDto item) {
        log.info("Add new item from user {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден."));
        Item model = ItemMapper.toItem(item);
        model.setOwner(user);
        return ItemMapper.toItemDto(itemRepository.save(model));
    }

    @Override
    @Transactional
    public ItemDto update(Long itemId, ItemUpdateDto item, Long userId) {
        log.info("Update item from user {}", userId);
        // ищем пользователя по id, проверяем существует ли он ?
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден."));
        Item model = itemRepository.findById(itemId)
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
    public List<ItemDto> search(String textSearch) {
        log.info("Search items by text {}", textSearch);
        if (textSearch.isEmpty()) {
            return List.of();
        }
        return itemRepository.search(textSearch).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
