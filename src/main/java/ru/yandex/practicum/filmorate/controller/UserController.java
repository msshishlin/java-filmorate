package ru.yandex.practicum.filmorate.controller;

// region imports

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.MissedEntityIdException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

// endregion

/**
 * Контроллер, обрабатывающий запросы пользователей.
 */
@RequestMapping("/users")
@RestController
@Slf4j
public final class UserController {
    /**
     * Сервис для работы с пользователями.
     */
    private final UserService userService;

    /**
     * Конструктор.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Создать пользователя.
     *
     * @param user   пользователь.
     * @param result результат привязки тела запроса к полям модели.
     * @return созданный пользователь.
     */
    @PostMapping
    public User create(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result.getAllErrors());
        }

        return this.userService.create(user);
    }

    /**
     * Получить коллекцию всех пользователей.
     *
     * @return коллекция пользователей.
     */
    @GetMapping
    public Collection<User> getAll() {
        return this.userService.getAll();
    }

    /**
     * Обновить пользователя.
     *
     * @param user   пользователя.
     * @param result результат привязки тела запроса к полям модели.
     * @return обновленный пользователь.
     */
    @PutMapping
    public User update(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result.getAllErrors());
        }

        if (user.getId() == null) {
            throw new MissedEntityIdException("Не задан идентификатор пользователя");
        }

        return this.userService.update(user);
    }

    /**
     * Добавить в друзья.
     *
     * @param userId   идентификатор пользователя.
     * @param friendId идентификатор друга.
     */
    @PutMapping("/{userId}/friends/{friendId}")
    public User addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        return this.userService.addFriend(userId, friendId);
    }

    /**
     * Получить друзей пользователя.
     *
     * @param userId идентификатор пользователя.
     * @return список друзей пользователя.
     */
    @GetMapping("/{userId}/friends")
    public Collection<User> getFriends(@PathVariable Long userId) {
        return this.userService.getFriends(userId);
    }

    /**
     * Получить общих друзей двух пользователей.
     *
     * @param userId      идентификатор пользователя.
     * @param otherUserId идентификатор другого пользователя.
     * @return список общих друзей двух пользователей.
     */
    @GetMapping("/{userId}/friends/common/{otherUserId}")
    public Collection<User> getFriends(@PathVariable Long userId, @PathVariable Long otherUserId) {
        return this.userService.getCommonFriends(userId, otherUserId);
    }

    /**
     * Удалить из друзей.
     *
     * @param userId   идентификатор пользователя.
     * @param friendId идентификатор друга.
     */
    @DeleteMapping("/{userId}/friends/{friendId}")
    public User removeFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        return this.userService.removeFriend(userId, friendId);
    }
}
