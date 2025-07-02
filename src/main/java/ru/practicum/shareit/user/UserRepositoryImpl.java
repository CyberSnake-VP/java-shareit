package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final Map<Long, User> users = new HashMap<>();
    private long idGenerator = 0;

    @Override
    public List<User> findAll() {
        log.info("Find all users");
        return new ArrayList<>(users.values());
    }

    @Override
    public User save(User user) {
        log.info("Save user: {}", user);
        if(user.getId() == null) {
            user.setId(++idGenerator);
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User findById(Long id) {
        log.info("Find user by id: {}", id);
        return users.get(id);
    }

    @Override
    public void delete(Long id) {
        log.info("Delete user by id: {}", id);
        users.remove(id);
    }
}
