package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    User save(User user);
    User findById(Long id);
    void delete(Long id);
}
