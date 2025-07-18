package ru.practicum.shareit.exception.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest({ErrorHandler.class, ErrorHandlerTest.TestController.class})
class ErrorHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestController testController; // Мокаем тестовый контроллер

    @Test
    void getItem_ThrowExceptionNotFoundException() throws Exception {
        doThrow(new NotFoundException("404 NotFoundException {}"))
                .when(testController).testMethod();
        mockMvc.perform(get("/test"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getItem_ThrowExceptionValidationException() throws Exception {
        doThrow(new ValidationException("409 ValidationException {}"))
                .when(testController).testMethod();
        mockMvc.perform(get("/test"))
                .andExpect(status().isConflict());
    }

    @Test
    void getItem_ThrowExceptionDuplacateDateException() throws Exception {
        doThrow(new ValidationException("409 DuplacateDateException {}"))
                .when(testController).testMethod();
        mockMvc.perform(get("/test"))
                .andExpect(status().isConflict());
    }

    @Test
    void getItem_ThrowExceptionMethodArgumentNotValidException() throws Exception {
        doThrow(new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR))
                .when(testController).testMethod();
        mockMvc.perform(get("/test"))
                .andExpect(status().is5xxServerError());
    }


    // тестовый контроллер для получения исключений
    @RestController
    @RequestMapping("/test")
    static class TestController {
        @GetMapping
        public void testMethod() {
            // Метод будет переопределён в моке для выброса исключений
        }
    }
}