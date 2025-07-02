package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ValidationException;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        log.info("Get all users");
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        log.info("Get user by id: {}", id);
        return userRepository.findById(id);
    }

    @Override
    public User saveUser(User user) {
        log.info("Save user: {}", user);
        if(isEmailExists(user)) {
            throw new ValidationException("Данный email уже используется.");
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Delete user by id: {}", id);
        userRepository.delete(id);
    }

    @Override
    public User updateUser(Long userId, User user) {
        log.info("Update user: {}", user);
        User userToUpdate = userRepository.findById(userId);
        if(userToUpdate == null) {
            throw new ValidationException("Пользователя c id "+ userId + "не существует.");
        }
        if(user.getName() == null && user.getEmail() == null) {
            throw new ValidationException("Все поля для изменения пусты. Необходимо задать значения хотя бы одного поля.");
        }
        if(user.getEmail() != null && !isEmailExists(user)) {

        }
        if(user.getName() != null) {

        }



        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        return userRepository.save(userToUpdate);
    }

    public boolean isEmailExists(User user) {
        return userRepository.findAll()
                .stream()
                .anyMatch(u -> u.getEmail().equals(user.getEmail()));
    }
}
