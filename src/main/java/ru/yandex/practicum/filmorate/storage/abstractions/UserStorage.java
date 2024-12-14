package ru.yandex.practicum.filmorate.storage.abstractions;

// region imports

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

// endregion

/**
 * Контракт хранилища пользователей.
 */
public interface UserStorage {
    /**
     * Создать пользователя.
     *
     * @param user пользователь.
     * @return созданный пользователь.
     */
    User create(User user);

    /**
     * Получить список всех пользователей.
     *
     * @return список всех пользователей.
     */
    Collection<User> getAll();

    /**
     * Обновить пользователя.
     *
     * @param user пользователь.
     * @return обновленный пользователь.
     */
    User update(User user);
}
