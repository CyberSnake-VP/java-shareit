package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestExitDto;
import ru.practicum.shareit.request.dto.ItemRequestEntryDto;
import ru.practicum.shareit.request.dto.ItemRequestExitNoItemListDto;

import java.util.List;

public interface ItemRequestService {

    ItemRequestExitNoItemListDto create(Long userId, ItemRequestEntryDto request);

    List<ItemRequestExitDto> getAll();

    ItemRequestExitDto getById(Long requestId);

    List<ItemRequestExitDto> getByRequestorId(Long requestorId);

}

