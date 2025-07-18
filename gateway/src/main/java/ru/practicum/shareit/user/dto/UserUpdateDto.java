package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDto {
    @Size(min = 5, max = 50, message = "имя не должно быть слишком длинным или коротким.")
    private String name;
    @Email(message = "Email некорректный")
    private String email;
}

