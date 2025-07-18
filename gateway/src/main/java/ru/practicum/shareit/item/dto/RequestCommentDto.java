package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestCommentDto {
    // Проверим, что текст комментария не пустой.
    // в тестах в тело идет одно поле текст, поэтому dto с одним полем.
    @NotBlank
    private String text;
}