package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;

    private ItemRequestDto itemDtoCreateRequest;

    @Test
    void addItem_ReturnCreatedItem() throws Exception {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("hammer");
        itemDto.setDescription("descriptionhammer");
        itemDto.setAvailable(true);

        itemDtoCreateRequest = new ItemRequestDto();
        itemDtoCreateRequest.setName("hammer");
        itemDtoCreateRequest.setDescription("descriptionhammer");
        itemDtoCreateRequest.setAvailable(true);

        when(itemService.add(anyLong(), any(ItemRequestDto.class))).thenReturn(itemDto);
        mockMvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new ItemRequestDto(1L, "hammer", "descriptionhammer",
                                        true, 1L))))
                .andExpect(status().isCreated());
    }

    @Test
    void addCommentToItem_ReturnCreatedItem() throws Exception {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("hammer");
        itemDto.setDescription("descriptionhammer");
        itemDto.setAvailable(true);

        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setText("test");
        commentDto.setAuthorName("booker");
        commentDto.setCreated(LocalDateTime.now());

        when(itemService.addComment(anyLong(), anyLong(), any(CommentDto.class))).thenReturn(commentDto);
        mockMvc.perform(post("/items/1/comment")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CommentDto(1L, "test", "booker",
                                        LocalDateTime.now()))))
                .andExpect(status().isCreated());
    }

    @Test
    void updateItem_ReturnUpdatedItem() throws Exception {
        ItemUpdateDto itemDto = new ItemUpdateDto();
        itemDto.setName("hammer");
        itemDto.setDescription("descriptionhammer");
        itemDto.setAvailable(true);

        ItemRequestDto updatedItem = new ItemRequestDto();
        updatedItem.setName("new hammer");
        updatedItem.setDescription("new descriptionhammer");
        updatedItem.setAvailable(false);

        when(itemService.update(anyLong(), any(ItemUpdateDto.class), anyLong())).thenReturn(ItemMapper.toItemDto(ItemMapper.toItem(updatedItem)));

        mockMvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("new hammer")))
                .andExpect(jsonPath("$.description", Matchers.is("new descriptionhammer")))
                .andExpect(jsonPath("$.available", Matchers.is(false)));
    }

    @Test
    void getItem_ReturnItem() throws Exception {
        ItemBookingDateDto itemBookingDateDto = new ItemBookingDateDto();
        itemBookingDateDto.setId(1L);
        itemBookingDateDto.setName("hammer");
        itemBookingDateDto.setDescription("descriptionhammer");
        itemBookingDateDto.setAvailable(true);

        when(itemService.getById(anyLong()))
                .thenReturn(itemBookingDateDto);

        mockMvc.perform(get("/items/1")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("hammer")))
                .andExpect(jsonPath("$.description", Matchers.is("descriptionhammer")));
    }


    @Test
    void getItemByUserId_ReturnItem() throws Exception {
        ItemBookingDateDto itemBookingDateDto = new ItemBookingDateDto();
        itemBookingDateDto.setId(1L);
        itemBookingDateDto.setName("hammer");
        itemBookingDateDto.setDescription("descriptionhammer");
        itemBookingDateDto.setAvailable(true);
        List<ItemBookingDateDto> searchResults = List.of(itemBookingDateDto);

        when(itemService.getAllByUser(anyLong())).thenReturn(searchResults);
        mockMvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk());
    }


    @Test
    void searchItems_ReturnMatchingItems() throws Exception {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("hammer");
        itemDto.setDescription("super hammer");
        itemDto.setAvailable(true);

        List<ItemDto> searchResults = List.of(itemDto);

        when(itemService.search(any())).thenReturn(searchResults);

        mockMvc.perform(get("/items/search")
                        .param("text", "hammer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("hammer")));
    }

}