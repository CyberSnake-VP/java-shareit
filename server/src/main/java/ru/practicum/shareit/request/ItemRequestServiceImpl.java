package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestEntryDto;
import ru.practicum.shareit.request.dto.ItemRequestExitDto;
import ru.practicum.shareit.request.dto.ItemRequestExitNoItemListDto;
import ru.practicum.shareit.request.dto.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;


@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public ItemRequestExitNoItemListDto create(Long userId, ItemRequestEntryDto request) {
        log.info("Create request for user: {} with request: {}", userId, request);
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь c id " + userId + "не найден."));
        ItemRequest itemRequest = ItemRequestMapper.mapToItemRequest(request, userId);
        return ItemRequestMapper.mapToItemRequestExitNoItemListDto(itemRequestRepository.save(itemRequest));
    }

    @Override
    public List<ItemRequestExitDto> getAll() {
        log.info("Get all requests for user: {}", userRepository.findAll());
        List<ItemRequestExitDto> result = itemRequestRepository.findAllByOrderByCreatedDesc().stream()
                .map(ItemRequestMapper::mapToItemRequestExitDto)
                .toList();
        // Находим все items по полю request, которое добавили сущности item. Это id запроса.
        //Если есть id запроса, значит item создан в ответ на запрос. Получаем список items и кладем его в dto
        for (ItemRequestExitDto itemRequest : result) {
            itemRequest.setItems(itemRepository.findByRequest(itemRequest.getId())
                    .stream().map(ItemMapper::toItemDto).toList());
        }
        return result;
    }

    @Override
    public ItemRequestExitDto getById(Long requestId) {
        /** Получаем запрос из бд, получаем список всех item'ов у которых request содержит искомый id и записываем в dto */
        ItemRequest request = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запрос c id " + requestId + "не найден."));
        ItemRequestExitDto result = ItemRequestMapper.mapToItemRequestExitDto(request);
        List<ItemDto> items = itemRepository.findByRequest(requestId).stream()
                .map(ItemMapper::toItemDto)
                .toList();
        result.setItems(items);
        return result;
    }

    @Override
    public List<ItemRequestExitDto> getByRequestorId(Long requestorId) {
        List<ItemRequestExitDto> result = itemRequestRepository.findAllByRequestorOrderByCreatedDesc(requestorId).stream()
                .map(ItemRequestMapper::mapToItemRequestExitDto)
                .toList();
        for (ItemRequestExitDto itemRequest : result) {
            itemRequest.setItems(itemRepository.findByRequest(itemRequest.getId())
                    .stream().map(ItemMapper::toItemDto).toList());
        }
        return result;
    }
}
