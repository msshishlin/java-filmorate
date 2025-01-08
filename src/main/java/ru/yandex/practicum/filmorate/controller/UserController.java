package ru.yandex.practicum.filmorate.controller;

// region imports

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.MissedEntityIdException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
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
     * @param userDto трансферный объект для сущности "Пользователь".
     * @param result  результат привязки тела запроса к полям модели.
     * @return созданный пользователь.
     */
    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result.getAllErrors());
        }

        return UserMapper.mapToUserDto(this.userService.create(UserMapper.mapToUser(userDto)));
    }

    /**
     * Получить коллекцию всех пользователей.
     *
     * @return коллекция пользователей.
     */
    @GetMapping
    public Collection<UserDto> getAll() {
        return UserMapper.mapToUserCollectionDto(this.userService.getAll());
    }

    /**
     * Обновить пользователя.
     *
     * @param userDto трансферный объект для сущности "Пользователь".
     * @param result  результат привязки тела запроса к полям модели.
     * @return обновленный пользователь.
     */
    @PutMapping
    public UserDto update(@Valid @RequestBody UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result.getAllErrors());
        }

        if (userDto.getId() == null) {
            throw new MissedEntityIdException("Не задан идентификатор пользователя");
        }

        return UserMapper.mapToUserDto(this.userService.update(UserMapper.mapToUser(userDto)));
    }

    /**
     * Добавить в друзья.
     *
     * @param userId   идентификатор пользователя.
     * @param friendId идентификатор друга.
     */
    @PutMapping("/{userId}/friends/{friendId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        this.userService.addFriend(userId, friendId);
    }

    /**
     * Получить друзей пользователя.
     *
     * @param userId идентификатор пользователя.
     * @return список друзей пользователя.
     */
    @GetMapping("/{userId}/friends")
    public Collection<UserDto> getFriends(@PathVariable Long userId) {
        return UserMapper.mapToUserCollectionDto(this.userService.getFriends(userId));
    }

    /**
     * Получить общих друзей двух пользователей.
     *
     * @param userId      идентификатор пользователя.
     * @param otherUserId идентификатор другого пользователя.
     * @return список общих друзей двух пользователей.
     */
    @GetMapping("/{userId}/friends/common/{otherUserId}")
    public Collection<UserDto> getFriends(@PathVariable Long userId, @PathVariable Long otherUserId) {
        return UserMapper.mapToUserCollectionDto(this.userService.getCommonFriends(userId, otherUserId));
    }

    /**
     * Удалить из друзей.
     *
     * @param userId   идентификатор пользователя.
     * @param friendId идентификатор друга.
     */
    @DeleteMapping("/{userId}/friends/{friendId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        this.userService.removeFriend(userId, friendId);
    }
}
