package ru.yandex.practicum.filmorate.storage;

// region imports

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.UpdateEntityException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.abstractions.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// endregion

/**
 * Хранилище пользователей в оперативной памяти компьютера.
 */
@Component
public class InMemoryUserStorage implements UserStorage {
    /**
     * Список пользователей.
     */
    private final Map<Long, User> users;

    /**
     * Конструктор.
     */
    public InMemoryUserStorage() {
        this.users = new HashMap<>();
    }

    /**
     * Создать пользователя.
     *
     * @param user пользователь.
     * @return созданный пользователь.
     */
    public User create(User user) {
        this.users.put(user.getId(), user);
        return user;
    }

    /**
     * Получить список всех пользователей.
     *
     * @return список всех пользователей.
     */
    public Collection<User> getAll() {
        return this.users.values();
    }

    /**
     * Обновить пользователя.
     *
     * @param user пользователь.
     * @return обновленный пользователь.
     */
    public User update(User user) {
        User oldUser = this.users.get(user.getId());
        if (oldUser == null) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден", user.getId()));
        }

        if (this.users.values().stream().anyMatch(u -> !Objects.equals(u.getId(), user.getId()) && Objects.equals(u.getEmail(), user.getEmail()))) {
            throw new UpdateEntityException("Новый e-mail пользователя (" + user.getEmail() + ") уже используется");
        }

        oldUser.setBirthday(user.getBirthday());
        oldUser.setEmail(user.getEmail());
        oldUser.setLogin(user.getLogin());
        oldUser.setName(user.getName());

        return oldUser;
    }
}
