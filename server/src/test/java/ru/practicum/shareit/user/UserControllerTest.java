package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;


    @Test
    void getUser_ReturnUser() throws Exception {
        UserDto userDto = new UserDto(1L, "user", "user@user.com");

        when(userService.getUserById(anyLong())).thenReturn(userDto);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("user")))
                .andExpect(jsonPath("$.email", is("user@user.com")));
    }

    @Test
    void deleteUser_ReturnNoContent() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateUser_ReturnUpdatedUser() throws Exception {
        UserDto updateDto = new UserDto(0L, "user", "user@user.com");
        UserDto updatedUser = new UserDto(1L, "user22", "user22@user22.com");
        when(userService.updateUser(anyLong(), any(UserDto.class))).thenReturn(updatedUser);

        mockMvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("user22")))
                .andExpect(jsonPath("$.email", is("user22@user22.com")));
    }

    @Test
    void getAllUsers_ReturnUserList() throws Exception {
        List<UserDto> users = List.of(
                new UserDto(0L, "user", "user@user.com"),
                new UserDto(1L, "user22", "user22@user22.com")
        );

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].name", is("user")))
                .andExpect(jsonPath("$[0].email", is("user@user.com")))
                .andExpect(jsonPath("$[1].id", is(1)))
                .andExpect(jsonPath("$[1].name", is("user22")))
                .andExpect(jsonPath("$[1].email", is("user22@user22.com")));
    }

    @Test
    void createUser_ReturnCreatedUser() throws Exception {

        UserDto user = userService.saveUser(new UserDto(1L, "user", "user@user.com"));

        when(userService.saveUser(any(UserDto.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserDto(1L, "user", "user@user.com"))))
                .andExpect(status().isCreated());
    }
}