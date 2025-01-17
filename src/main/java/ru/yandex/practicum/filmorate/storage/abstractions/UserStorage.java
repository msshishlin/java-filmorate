package ru.yandex.practicum.filmorate.storage.abstractions;

// region imports

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

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
     * Найти пользователя по его идентификатору.
     *
     * @param userId идентификатор пользователя.
     * @return пользователь.
     */
    Optional<User> findById(Long userId);

    /**
     * Обновить пользователя.
     *
     * @param user пользователь.
     * @return обновленный пользователь.
     */
    User update(User user);

    /**
     * Добавить в друзья.
     *
     * @param userId   идентификатор пользователя.
     * @param friendId идентификатор друга.
     */
    void addFriend(Long userId, Long friendId);

    /**
     * Получить друзей пользователя.
     *
     * @param userId идентификатор пользователя.
     * @return список друзей пользователя.
     */
    Collection<User> getFriends(Long userId);

    /**
     * Получить общих друзей двух пользователей.
     *
     * @param userId      идентификатор пользователя.
     * @param otherUserId идентификатор другого пользователя.
     * @return список общих друзей двух пользователей.
     */
    Collection<User> getCommonFriends(Long userId, Long otherUserId);

    /**
     * Удалить из друзей.
     *
     * @param userId   идентификатор пользователя.
     * @param friendId идентификатор друга.
     */
    void removeFriend(Long userId, Long friendId);
}
