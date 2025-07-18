package ru.practicum.shareit.item;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ItemDtoTest {


    @Autowired
    private JacksonTester<Item> json;

    @Test
    void testSerialize() throws Exception {
        ItemRequestDto dto = new ItemRequestDto(
                1L,
                "test",
                "test@test.ru",
                true,
                1L
        );

        var result = json.write(ItemMapper.toItem(dto));

        assertThat(result)
                .hasJsonPath("$.id")
                .hasJsonPath("$.name")
                .hasJsonPath("$.description")
                .hasJsonPath("$.available")
                .hasJsonPath("$.request");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo(dto.getName());
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(dto.getDescription());
        assertThat(result).extractingJsonPathBooleanValue("$.available").isEqualTo(dto.getAvailable());
        assertThat(result).extractingJsonPathNumberValue("$.request").isEqualTo(1);
    }


    @Test
    void testMappToItem() throws Exception {
        ItemRequestDto dto = new ItemRequestDto(
                1L,
                "test",
                "test@test.ru",
                true,
                1L
        );
        var result = ItemMapper.toItem(dto);
        assertThat(Objects.equals(result.getId(), dto.getId()));
        assertThat(result.getDescription().equals(dto.getDescription()));
        assertThat(result.getName().equals(dto.getName()));
        assertThat(result.getAvailable() == (dto.getAvailable()));
    }

    @Test
    void testMapToItemDtoRequest() throws Exception {
        var dto = new Item();
        dto.setId(1L);
        dto.setName("test");
        dto.setDescription("test@test.ru");
        dto.setAvailable(true);
        dto.setOwner(new User(1L, "test", "email@yandex.ru"));
        dto.setRequest(1L);

        var result = ItemMapper.toItemDto(dto);
        assertThat(Objects.equals(result.getId(), dto.getId()));
        assertThat(result.getDescription().equals(dto.getDescription()));
        assertThat(result.getName().equals(dto.getName()));
        assertThat(result.getAvailable() == (dto.getAvailable()));
    }

    @Test
    void testMapptoItemBookingDto() throws Exception {
        var dto = new Item();
        dto.setId(1L);
        dto.setName("test");
        dto.setDescription("test@test.ru");
        dto.setAvailable(true);
        dto.setOwner(new User(1L, "test", "email@yandex.ru"));
        dto.setRequest(1L);

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("test");
        comment.setAuthor(new User(1L, "test", "email@yandex.ru"));
        List<Comment> comments = List.of(comment);

        var result = ItemMapper.mapToItemBookingDateDto(dto, comments);
        assertThat(Objects.equals(result.getId(), dto.getId()));
        assertThat(result.getDescription().equals(dto.getDescription()));
        assertThat(result.getName().equals(dto.getName()));
        assertThat(result.getAvailable() == (dto.getAvailable()));
    }
}
