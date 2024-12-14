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
}
