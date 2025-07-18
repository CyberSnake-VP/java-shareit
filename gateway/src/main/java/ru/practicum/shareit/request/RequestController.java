package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static ru.practicum.shareit.client.BaseClient.USER_ID_HEADER;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RequestController {

    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestHeader(USER_ID_HEADER) @Positive long userId,
                                                @Valid @RequestBody ItemRequestDto requestDto) {
        log.info("createRequest from userId={}", userId);
        return requestClient.createRequest(userId, requestDto);
    }

    @GetMapping
    public ResponseEntity<Object> getUserRequests(@RequestHeader(USER_ID_HEADER) @Positive long userId) {
        log.info("getUserRequests for userId={}", userId);
        return requestClient.getUserRequests(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@RequestHeader(USER_ID_HEADER) @Positive long userId) {
        log.info("getAllRequests for all by userId={}", userId);
        return requestClient.getAllRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestById(@RequestHeader(USER_ID_HEADER) @Positive long userId,
                                                 @PathVariable long requestId) {
        log.info("GET /requests/{} for userId={}", requestId, userId);
        return requestClient.getRequestById(userId, requestId);
    }
}
