package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Comment;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.dto.mapper.CommentMapper;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
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
    private final CommentRepository commentRepository;

    /**
     * В этом методе получаем объект бронирования, после чего маппим его в нужный нам объект dto получая значение
     * полей startDate и endDate. Использовал map, чтобы получить id item'а в качестве ключа.
     */
    @Override
    public List<ItemBookingDateDto> getAllByUser(Long userId) {
        log.info("Get items from user {}", userId);

        Map<Long, Item> items = itemRepository.findByOwner_Id(userId).stream()
                .collect(Collectors.toMap(Item::getId, Function.identity()));
        Map<Long, Booking> bookings = bookingRepository.findAllByItem_Owner_IdOrderByStartDesc(userId).stream()
                .collect(Collectors.toMap(booking -> booking.getItem().getId(), Function.identity()));

        List<Comment> comments = commentRepository.findAllByAuthor_Id(userId);
        return ItemMapper.mapToItemBookingDateDto(bookings, items, comments);
    }

    @Override
    public ItemBookingDateDto getById(Long itemId) {
        log.info("Get item from item {}", itemId);
        List<Comment> comments = commentRepository.findAllByItem_Id(itemId);
        return itemRepository.findById(itemId).stream()
                .map(item -> ItemMapper.mapToItemBookingDateDto(item, comments))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Item not found"));
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

    @Override
    @Transactional
    public CommentDto addComment(Long userId, Long itemId, CommentDto commentDto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("item not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user not found"));

        /** Через запросный метод получаем  булевое значение имеется ли вещь и пользователь осуществивший ее бронь
         * а также заодно проверяем была ли одобрена бронь, а так же время бронирования не должно быть в будущем.**/
        Boolean isBooking = bookingRepository.existsByItem_IdAndBooker_IdAndStatusAndStartBefore(
                itemId, userId, BookingStatus.APPROVED, LocalDateTime.now()
        );
        if (!isBooking) {
            throw new IllegalArgumentException("booking doesn't not exist");
        }

        Comment comment = commentRepository.save(CommentMapper.mapToComment(commentDto, user, item));
        return CommentMapper.mapToCommentDto(comment);
    }
}
