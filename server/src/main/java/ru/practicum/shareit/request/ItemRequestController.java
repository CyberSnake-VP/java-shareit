package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestEntryDto;
import ru.practicum.shareit.request.dto.ItemRequestExitDto;
import ru.practicum.shareit.request.dto.ItemRequestExitNoItemListDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;


@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRequestExitNoItemListDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                                               @RequestBody final ItemRequestEntryDto request) {
        return itemRequestService.create(userId, request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemRequestExitDto> getByRequestorId(@RequestHeader("X-Sharer-User-Id") Long requestorId) {
        return itemRequestService.getByRequestorId(requestorId);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemRequestExitDto> getAll() {
        return itemRequestService.getAll();
    }

    @GetMapping("/{requestId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemRequestExitDto getById(@PathVariable("requestId") final Long requestId) {
        return itemRequestService.getById(requestId);
    }

}
