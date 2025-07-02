package ru.practicum.shareit.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Data
public class User {
    @Positive(message = "Поле id должно быть положительным числом.")
    private Long id;
    private String name;
    @NotBlank(message = "Поле email должно быть заполнено.")
    @Email(message = "Email не заполнен или не корректен.")
    private String email;
}
