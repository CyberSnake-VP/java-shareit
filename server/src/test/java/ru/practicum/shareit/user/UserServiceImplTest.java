package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto(
                null,
                "Vadim",
                "vad@yandex.ru"
        );

        userDto = new UserDto(
                null,
                "Sofia",
                "sof@yandex.ru"
        );
    }

    @Test
    void updateUser_ThrowExceptionIfUserNotFound() {
        UserDto updateDto = new UserDto();
        updateDto.setName("new Name");

        assertThatThrownBy(() -> userService.updateUser(999L, updateDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Пользователь с id 999 не найден.");
    }

    @Test
    void deleteUser_RemoveUser() {
        UserDto createdUser = userService.saveUser(userDto);

        userService.deleteUser(createdUser.getId());

        assertThatThrownBy(() -> userService.getUserById(createdUser.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Пользователь с id " + createdUser.getId() + " не найден.");
    }

    @Test
    void updateUser_UpdateAndReturnUpdatedUser() {
        UserDto createdUser = userService.saveUser(userDto);

        UserDto updateDto = new UserDto();
        updateDto.setName("vadim");
        updateDto.setEmail("new@new.com");

        UserDto updatedUser = userService.updateUser(createdUser.getId(), updateDto);

        assertThat(updatedUser.getId()).isEqualTo(createdUser.getId());
        assertThat(updatedUser.getName()).isEqualTo("vadim");
        assertThat(updatedUser.getEmail()).isEqualTo("new@new.com");
    }

    @Test
    void getUser_ThrowExceptionWhenNotFound() {

        assertThatThrownBy(() -> userService.getUserById(999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Пользователь с id 999 не найден.");
    }

    @Test
    void getAllUsers_ReturnAllUsers() {
        userService.saveUser(userDto);
        userService.saveUser(new UserDto(null, "test", "vad@yandex.com"));

        List<UserDto> users = userService.getAllUsers().stream().toList();

        assertThat(users).hasSize(2);
    }

    @Test
    void getUser_ReturnExistingUser() {

        UserDto createdUser = userService.saveUser(userDto);

        UserDto foundUser = userService.getUserById(createdUser.getId());

        assertThat(foundUser).isNotNull();
        assertThat(Objects.equals(foundUser.getId(), createdUser.getId()));
        assertThat(foundUser.getName()).isEqualTo(createdUser.getName());
        assertThat(foundUser.getEmail()).isEqualTo(createdUser.getEmail());
    }

    @Test
    void createUser_SaveAndReturnUser() {

        UserDto createdUser = userService.saveUser(userDto);
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getName()).isEqualTo(userDto.getName());
        assertThat(createdUser.getEmail()).isEqualTo(userDto.getEmail());
    }

    @Test
    void createUserErrorEmail_SaveAndReturnUser() {
        UserDto userDtoCreate = new UserDto();
        userDtoCreate.setName("test");
        userDtoCreate.setEmail("test@test.com");
        userService.saveUser(userDtoCreate);

        assertThatThrownBy(() -> userService.saveUser(userDtoCreate))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageStartingWith("could not execute statement [Unique index or primary key violation: ");
    }

    @Test
    void updateUserErrorEmail_UpdateAndReturnUpdatedUser() {

        UserDto userDtoCreate = new UserDto();
        userDtoCreate.setName("test2");
        userDtoCreate.setEmail("test2@test2.com");
        UserDto createdUser = userService.saveUser(userDtoCreate);


        assertThatThrownBy(() -> userService.updateUser(createdUser.getId(), createdUser))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Email уже существует.");

    }
}