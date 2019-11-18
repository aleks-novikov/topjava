package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private AtomicInteger counter = new AtomicInteger(0);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();

    @Override
    public boolean delete(int id) {
        log.info("delete user with id {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save user {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }

        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get user with id {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("get all users");
        List<User> users = new ArrayList<>(repository.values());
        users.sort(Comparator.comparing(User::getName)
                             .thenComparing(User::getId));
        return users;
    }

    @Override
    public User getByEmail(String email) {
        log.info("get user by email {}", email);
        return repository.values().stream().filter(user -> user.getEmail().equals(email))
                                           .findAny().orElse(null);
    }
}
