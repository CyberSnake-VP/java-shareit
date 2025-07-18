package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestExitDto;
import ru.practicum.shareit.request.dto.ItemRequestEntryDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
     private final ItemRequestRepository itemRequestRepository;
     private final ItemRepository itemRepository;
     private final UserRepository userRepository;


     @Override
     public ItemRequestEntryDto create(Long userId, ItemRequestExitDto request) {
          return null;
     }

     @Override
     public List<ItemRequestEntryDto> getAll(Long userId) {
          return null;
     }

     @Override
     public ItemRequestEntryDto getById(Long userId, Long requestId) {
          return null;
     }

     @Override
     public List<ItemRequestEntryDto> getByUser(Long userId) {
          return null;
     }
}
