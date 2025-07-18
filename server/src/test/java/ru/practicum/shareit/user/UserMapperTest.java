package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.mapper.UserMapper;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {
    private UserDto newUserDto;

    @BeforeEach
    void setUp() {
        newUserDto = new UserDto(
                null,
                "Vadim",
                "vad@yandex.ru"
        );
    }

    @Test
    void shouldMapToEntityFromNewUserDto() {
        User user = UserMapper.toUser(newUserDto);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isNull();
        assertThat(user.getName()).isEqualTo(newUserDto.getName());
        assertThat(user.getEmail()).isEqualTo(newUserDto.getEmail());
    }

    @Test
    void shouldMapToDtoFromUser() {
        User user = new User(
                1L,
                "Sofia",
                "sof@yandex.ru"
        );

        UserDto dto = UserMapper.toUserDto(user);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(user.getId());
        assertThat(dto.getName()).isEqualTo(user.getName());
        assertThat(dto.getEmail()).isEqualTo(user.getEmail());
    }

}