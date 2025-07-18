package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.dto.ItemRequestEntryDto;
import ru.practicum.shareit.request.dto.ItemRequestExitDto;
import ru.practicum.shareit.request.dto.ItemRequestExitNoItemListDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ItemRequestController.class)
class ItemRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemRequestService itemRequestService;

    @Test
    void getRequestById_ReturnRequest() throws Exception {
        ItemRequestExitDto requestDto = new ItemRequestExitDto();
        requestDto.setId(1L);
        requestDto.setDescription("ttt");
        requestDto.setCreated(LocalDateTime.now());

        when(itemRequestService.getById(anyLong())).thenReturn(requestDto);

        mockMvc.perform(get("/requests/1")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("ttt")))
                .andExpect(jsonPath("$.created").isNotEmpty());
    }


    @Test
    void getAllItemRequests_ReturnRequest() throws Exception {
        ItemRequestExitDto requestDto = new ItemRequestExitDto();
        requestDto.setId(1L);
        requestDto.setDescription("ttt");
        requestDto.setCreated(LocalDateTime.now());

        List<ItemRequestExitDto> requests = List.of(requestDto);
        when(itemRequestService.getAll()).thenReturn(requests);

        mockMvc.perform(get("/requests/1")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk());

    }


    @Test
    void createRequest_ReturnCreatedRequest() throws Exception {

        ItemRequestEntryDto itemRequestDto = new ItemRequestEntryDto();
        itemRequestDto.setDescription("ttt");

        ItemRequestExitNoItemListDto savedRequest = new ItemRequestExitNoItemListDto();
        savedRequest.setId(1L);
        savedRequest.setDescription("ttt");
        savedRequest.setCreated(LocalDateTime.now());

        when(itemRequestService.create(anyLong(), any(ItemRequestEntryDto.class))).thenReturn(savedRequest);

        mockMvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("ttt")))
                .andExpect(jsonPath("$.created").isNotEmpty());
    }

    @Test
    void getUserRequests_ReturnRequests() throws Exception {
        ItemRequestExitDto request1 = new ItemRequestExitDto();
        request1.setId(1L);
        request1.setDescription("ttt");
        request1.setCreated(LocalDateTime.now());

        ItemRequestExitDto request2 = new ItemRequestExitDto();
        request2.setId(2L);
        request2.setDescription("tttt");
        request2.setCreated(LocalDateTime.now());

        List<ItemRequestExitDto> requests = List.of(request1, request2);

        when(itemRequestService.getByRequestorId(anyLong())).thenReturn(requests);

        mockMvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].description", is("ttt")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].description", is("tttt")));
    }

    @Test
    void getAllRequests_Return() throws Exception {
        ItemRequestExitDto request1 = new ItemRequestExitDto();
        request1.setId(1L);
        request1.setDescription("ttt");
        request1.setCreated(LocalDateTime.now());
        ItemRequestExitDto request2 = new ItemRequestExitDto();
        request2.setId(2L);
        request2.setDescription("tttt");
        request2.setCreated(LocalDateTime.now());
        List<ItemRequestExitDto> requests = List.of(request1, request2);
        when(itemRequestService.getAll()).thenReturn(requests);
        mockMvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk());

    }
}
