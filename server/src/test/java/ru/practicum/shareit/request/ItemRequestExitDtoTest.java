package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.request.dto.ItemRequestExitDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ItemRequestExitDtoTest {
    @Autowired
    private JacksonTester<ItemRequestExitDto> json;

    @Test
    void testSerialize() throws Exception {
        LocalDateTime time = LocalDateTime.of(2025, 1, 1, 0, 0);
        ItemRequestDto item = new ItemRequestDto();
        item.setId(1L);
        item.setName("test");
        item.setDescription("test");
        item.setAvailable(true);
        item.setRequestId(1L);

        var dto = new ItemRequestExitDto();
        dto.setId(1L);
        dto.setDescription("test");
        dto.setCreated(time);
        dto.setRequestor(1L);
        dto.setItems(List.of(ItemMapper.toItemDto(ItemMapper.toItem(item))));

        var result = json.write(dto);

        assertThat(result)
                .hasJsonPath("$.id")
                .hasJsonPath("$.description")
                .hasJsonPath("$.requestor")
                .hasJsonPath("$.created")
                .hasJsonPath("$.items");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(dto.getDescription());
        assertThat(result).extractingJsonPathNumberValue("$.requestor").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo("2025-01-01T00:00:00");
        assertThat(result).extractingJsonPathNumberValue("$.items[0].id")
                .isEqualTo(dto.getItems().getFirst().getId().intValue());
        assertThat(result).extractingJsonPathStringValue("$.items[0].name")
                .isEqualTo(dto.getItems().getFirst().getName());
        assertThat(result).extractingJsonPathStringValue("$.items[0].description")
                .isEqualTo(dto.getItems().getFirst().getDescription());
        assertThat(result).extractingJsonPathBooleanValue("$.items[0].available")
                .isEqualTo(dto.getItems().getFirst().getAvailable());
        assertThat(result).extractingJsonPathNumberValue("$.items[0].ownerId");
    }
}