package ru.practicum.shareit.request;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestEntryDto;
import ru.practicum.shareit.request.dto.ItemRequestExitDto;

import java.util.List;


@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRequestExitDto create(@RequestHeader("X-Sharer-User-Id") @Positive Long userId,
                                     @RequestBody final ItemRequestEntryDto request) {
        return itemRequestService.create(userId, request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemRequestExitDto> getByUser(@RequestHeader("X-Sharer-User-Id") @Positive Long userId) {
        return itemRequestService.getByUser(userId);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemRequestExitDto> getAll(@RequestHeader("X-Sharer-User-Id") @Positive Long userId) {
        return itemRequestService.getAll(userId);
    }

    @GetMapping("/{requestId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemRequestExitDto getById(@RequestHeader("X-Sharer-User-Id") @Positive Long userId,
                                       @PathVariable("requestId") final Long requestId) {
        return itemRequestService.getById(userId, requestId);
    }

}
