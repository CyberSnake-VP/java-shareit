package ru.practicum.shareit.item.dto.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemBookingDateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


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

    /**
     * Получаем объект dto с установленными датами начала и конца бронирования
     */
    public static List<ItemBookingDateDto> mapToItemBookingDateDto(Map<Long, Booking> bookings, Map<Long, Item> items) {
        List<ItemBookingDateDto> result = new ArrayList<>();
        items.forEach((id, item) -> {
            result.add(ItemMapper.mapToItemBookingDateDto(item));
        });

        Set<Long> bookingIds = bookings.keySet();
        Set<Long> itemIds = items.keySet();
        for (Long bookingId : bookingIds) {
            if (itemIds.contains(bookingId)) {
                ItemBookingDateDto ib = ItemMapper.mapToItemBookingDateDto(items.get(bookingId));
                ib.setStartBookingDate(bookings.get(bookingId).getStart());
                ib.setEndBookingDate(bookings.get(bookingId).getEnd());
                result.add(ib);
            }
        }

        return result;
    }

    public static ItemBookingDateDto mapToItemBookingDateDto(Item item) {
        return new ItemBookingDateDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                null,
                null
        );
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
