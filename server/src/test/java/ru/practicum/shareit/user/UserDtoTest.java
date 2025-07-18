package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.mapper.UserMapper;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
class UserDtoTest {

    @Autowired
    private JacksonTester<UserDto> json;

    @Test
    void test_Serialize_to_Json() throws Exception {
        var dto = new UserDto(
                1L,
                "Vadim",
                "vad@yandex.ru"
        );

        var result = json.write(dto);

        assertThat(result)
                .hasJsonPath("$.id")
                .hasJsonPath("$.name")
                .hasJsonPath("$.email");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo(dto.getName());
        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo(dto.getEmail());
    }

    @Test
    void test_mapToUser() throws Exception {
        var dto = new UserDto(
                1L,
                "Vadim",
                "vad@yandex.ru"
        );
        var result = UserMapper.toUser(dto);
        assertThat(Objects.equals(result.getId(), dto.getId()));
        assertThat(result.getName().equals(dto.getName()));
        assertThat(result.getEmail().equals(dto.getEmail()));
    }
}