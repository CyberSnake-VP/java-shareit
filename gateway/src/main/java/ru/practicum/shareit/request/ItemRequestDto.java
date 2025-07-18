package ru.practicum.shareit.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemRequestDto {
    // в текстах в json прилетает только это поле, ограничим его размер.
    @Size(min = 10, max = 1000, message = "Слишком большое описание.")
    private String description;
}