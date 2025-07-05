package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemDto {
    @Positive(message = "Поле id должно быть положительным числом.")
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull(message = "Поле available не может быть пустым.")
    private Boolean available;
}
