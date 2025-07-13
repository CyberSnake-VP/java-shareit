package ru.practicum.shareit.booking;


import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.Instant;

public class Booking {
/**id — уникальный идентификатор бронирования;
 start — дата и время начала бронирования;
 end — дата и время конца бронирования;
 item — вещь, которую пользователь бронирует;
 booker — пользователь, который осуществляет бронирование;
 status — статус бронирования. Может принимать одно из следующих значений:
 WAITING — новое бронирование, ожидает одобрения, APPROVED — бронирование
 подтверждено владельцем, REJECTED — бронирование отклонено владельцем,
 CANCELED — бронирование отменено создателем.**/

private Long id;
private Instant start;
private Instant end;
private Item item;
private User booker;
private Status status;

}
