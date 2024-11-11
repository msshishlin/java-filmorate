package ru.yandex.practicum.filmorate.controller;

// region imports

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// endregion

/**
 * Контроллер, обрабатывающий запросы пользователей.
 */
@RequestMapping("/users")
@RestController
@Slf4j
public final class UserController {
    /**
     * Список пользователей.
     */
    private final Map<Long, User> users;

    /**
     * Конструктор.
     */
    public UserController() {
        this.users = new HashMap<>();
    }

    /**
     * Создать пользователя.
     *
     * @param user пользователь.
     * @return созданный пользователь.
     */
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.debug("Creating user {}", user);

        if (user.getName() == null) {
            user.setName(user.getLogin());
        }

        user.setId(this.getNextId());
        log.trace("Calculated user id: {}", user.getId());

        this.users.put(user.getId(), user);
        return user;
    }

    /**
     * Получить коллекцию всех пользователей.
     *
     * @return коллекция пользователей.
     */
    @GetMapping
    public Collection<User> getAll() {
        return this.users.values();
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        if (newUser.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Не задан идентификатор пользователя");
        }

        if (this.users.values().stream().anyMatch(u -> !Objects.equals(u.getId(), newUser.getId()) && Objects.equals(u.getEmail(), newUser.getEmail()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Новый e-mail пользователя уже используется");
        }

        if (!this.users.containsKey(newUser.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Пользователь с идентификатором %d не найден", newUser.getId()));
        }

        log.debug("Updating user {}", newUser);

        User oldUser = this.users.get(newUser.getId());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setLogin(newUser.getLogin());

        if (newUser.getName() != null) {
            oldUser.setName(newUser.getName());
        }

        oldUser.setBirthday(newUser.getBirthday());

        log.debug("Updated user {}", oldUser);
        return oldUser;
    }

    // region Facilities

    // вспомогательный метод для генерации идентификатора нового поста
    private long getNextId() {
        long currentMaxId = this.users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);

        return ++currentMaxId;
    }

    // endregion
}
