package ru.practicum.shareit.request;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;

import java.util.List;


@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRequestResponseDto create(@RequestHeader("X-Sharer-User-Id") @Positive Long userId,
                                         @RequestBody final ItemRequestDto itemRequestDto) {
        return null;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemRequestResponseDto> getByUser(@RequestHeader("X-Sharer-User-Id") @Positive Long userId) {
        return null;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemRequestResponseDto> getAll(@RequestHeader("X-Sharer-User-Id") @Positive Long userId) {
        return null;
    }

    @GetMapping("/{requestId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemRequestResponseDto getById(@RequestHeader("X-Sharer-User-Id") @Positive Long userId,
                                          @PathVariable("requestId") final Long requestId) {
        return null;
    }

}
