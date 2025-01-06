package ru.yandex.practicum.filmorate.service;

// region imports

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.abstractions.UserStorage;

import java.util.Collection;

// endregion

/**
 * Сервис для работы с пользователями.
 */
@Service
public class UserService {
    /**
     * Хранилище пользователей.
     */
    private final UserStorage userStorage;

    /**
     * Конструктор.
     *
     * @param userStorage хранилище пользователей.
     */
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    /**
     * Создать пользователя.
     *
     * @param user пользователь.
     * @return созданный пользователь.
     */
    public User create(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }

        user.setId(this.getNextId());
        return this.userStorage.create(user);
    }

    /**
     * Получить список всех пользователей.
     *
     * @return список всех пользователей.
     */
    public Collection<User> getAll() {
        return this.userStorage.getAll();
    }

    /**
     * Обновить пользователя.
     *
     * @param user пользователь.
     * @return обновленный пользователь.
     */
    public User update(User user) {
        return this.userStorage.update(user);
    }

    /**
     * Добавить в друзья.
     *
     * @param userId   идентификатор пользователя.
     * @param friendId идентификатор друга.
     * @return пользователь.
     */
    public User addFriend(Long userId, Long friendId) {
        return this.userStorage.addFriend(userId, friendId);
    }

    /**
     * Получить друзей пользователя.
     *
     * @param userId идентификатор пользователя.
     * @return список друзей пользователя.
     */
    public Collection<User> getFriends(Long userId) {
        return this.userStorage.getFriends(userId);
    }

    /**
     * Получить общих друзей двух пользователей.
     *
     * @param userId      идентификатор пользователя.
     * @param otherUserId идентификатор другого пользователя.
     * @return список общих друзей двух пользователей.
     */
    public Collection<User> getCommonFriends(Long userId, Long otherUserId) {
        return this.userStorage.getCommonFriends(userId, otherUserId);
    }

    /**
     * Удалить из друзей.
     *
     * @param userId   идентификатор пользователя.
     * @param friendId идентификатор друга.
     * @return пользователь.
     */
    public User removeFriend(Long userId, Long friendId) {
        return this.userStorage.removeFriend(userId, friendId);
    }

    // region Facilities

    /**
     * Получить идентификатор для создания нового пользователя.
     *
     * @return идентификатор для создания нового пользователя.
     */
    private long getNextId() {
        long currentMaxId = this.userStorage.getAll()
                .stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0);

        return ++currentMaxId;
    }

    // endregion
}
