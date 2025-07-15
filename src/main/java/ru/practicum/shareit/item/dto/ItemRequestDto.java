package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemRequestDto {
    private Long id;
    @NotEmpty
    private String name;
    private String description;
    @NotNull(message = "Поле available не может быть пустым.")
    private Boolean available;
}
