package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item add(@RequestHeader("X-Sharer-User-Id") Long userId,
                    @RequestBody Item item) {
        return itemService.addNewItem(userId, item);
    }

    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public Item get(@PathVariable("itemId") Long itemId) {
        return itemService.getItem(itemId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Item> getAllByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getItems(userId);
    }

    @PatchMapping("/{itemId}")
    public Item update(@RequestHeader("X-Sharer-User-Id") Long userId,
                       @PathVariable("itemId") Long itemId,
                       @RequestBody Item item) {
        return itemService.updateItem(itemId, item, userId);
    }

}
