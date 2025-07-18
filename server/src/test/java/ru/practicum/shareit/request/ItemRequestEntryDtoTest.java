package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.request.dto.ItemRequestEntryDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ItemRequestEntryDtoTest {
    @Autowired
    private JacksonTester<ItemRequestEntryDto> json;

    @Test
    void testSerialize() throws Exception {
        var dto = new ItemRequestEntryDto("test");

        var result = json.write(dto);

        assertThat(result).hasJsonPath("$.description");
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(dto.getDescription());
    }
}