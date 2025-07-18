package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestExitDto;
import ru.practicum.shareit.request.dto.ItemRequestEntryDto;

import java.util.List;

public interface ItemRequestService {

    ItemRequestEntryDto create(Long userId, ItemRequestExitDto request);

    List<ItemRequestEntryDto> getAll(Long userId);

    ItemRequestEntryDto getById(Long userId, Long requestId);

    List<ItemRequestEntryDto> getByUser(Long userId);

}

