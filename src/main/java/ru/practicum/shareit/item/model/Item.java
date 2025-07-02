package ru.practicum.shareit.item.model;


import lombok.Data;

@Data
public class Item {
    private String id;
    private String name;
    private Long ownerId;
    private String description;
    private Boolean available;
}
