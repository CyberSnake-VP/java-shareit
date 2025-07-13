package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        log.info("Get all users");
        return userRepository.findAll().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long userId) {
        log.info("Get user by id: {}", userId);
        return userRepository.findById(userId)
                .map(UserMapper::toUserDto)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден."));
    }

    @Override
    public UserDto saveUser(UserDto user) {
        log.info("Save user: {}", user);
        User userToSave = UserMapper.toUser(user);
        return UserMapper.toUserDto(userRepository.save(userToSave));
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Delete user by id: {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDto updateUser(Long userId, UserDto user) {
        log.info("Update user: {}", user);
        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден."));
        if (user.getName() != null) {
            userToUpdate.setName(user.getName());
        }
        if (user.getEmail() != null) {
            if (isEmailExists(user)) {
                throw new ValidationException("Email уже существует.");
            }
            userToUpdate.setEmail(user.getEmail());
        }

        return UserMapper.toUserDto(userRepository.save(userToUpdate));
    }

    public boolean isEmailExists(UserDto user) {
        User userWithEmail = userRepository.findByEmail(user.getEmail());
        return userWithEmail != null;
    }
}
