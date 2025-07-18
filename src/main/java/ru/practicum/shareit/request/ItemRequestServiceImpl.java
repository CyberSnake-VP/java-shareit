package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
     private final ItemRequestRepository itemRequestRepository;
     private final ItemRepository itemRepository;


     @Override
     public ItemRequestResponseDto create(Long userId, ItemRequestDto request) {
          return null;
     }

     @Override
     public List<ItemRequestResponseDto> getAll(Long userId) {
          return null;
     }

     @Override
     public ItemRequestResponseDto getById(Long userId, Long requestId) {
          return null;
     }

     @Override
     public List<ItemRequestResponseDto> getByUser(Long userId) {
          return null;
     }
}
