package ru.practicum.shareit.user;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User saveUser(User user);
    void deleteUser(Long id);
    User updateUser(Long userId, User user);
}
