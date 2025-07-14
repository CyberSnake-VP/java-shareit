package ru.practicum.shareit.item.dto.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemBookingDateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable()
        );
    }

    /**Получаем объект dto с установленными датами начала и конца бронирования*/
    public static List<ItemBookingDateDto> mapToItemBookingDateDto(List<Booking> bookings, List<Item> item) {
//        return booking.stream()
//                .map(it -> new ItemBookingDateDto(
//                        item.getItem().getId(),
//                        item.getItem().getName(),
//                        item.getItem().getDescription(),
//                        item.getItem().getAvailable(),
//                        item.getStart(),
//                        item.getEnd()
//                ))
//                .toList();
        List<ItemBookingDateDto> result = new ArrayList<>();

    }

    public static Item toItem(ItemRequestDto itemDto) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        return item;
    }
}
