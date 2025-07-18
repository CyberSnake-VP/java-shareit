package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserClient userClient;

    /**
     * Проверяем на валидность поля входящих dto. Разные dto при обновлении и создании.
     * Переносим контроллеры для валидации входных параметров.
     */

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("POST /users - создание пользователя с email '{}'", userDto.getEmail());
        return userClient.createUser(userDto);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable @Positive Long userId,
                                             @Valid @RequestBody UserUpdateDto userUpdateDto) {
        log.info("PATCH /users/{} - обновление пользователя", userId);
        return userClient.updateUser(userId, userUpdateDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable @Positive Long userId) {
        log.info("GET /users/{} - получение пользователя", userId);
        return userClient.getUserById(userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        log.info("GET /users - получение всех пользователей");
        return userClient.getAllUsers();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable @Positive Long userId) {
        log.info("DELETE /users/{} - удаление пользователя", userId);
        return userClient.deleteUser(userId);
    }
}
