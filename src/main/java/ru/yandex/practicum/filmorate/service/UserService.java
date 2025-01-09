package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.abstractions.UserStorage;

import java.util.Collection;

/**
 * Сервис для работы с пользователями.
 */
@Service
public class UserService {
    /**
     * Хранилище пользователей.
     */
    @Autowired
    @Qualifier(UserDbStorage.CLASS_NAME)
    private UserStorage userStorage;

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
     */
    public void addFriend(Long userId, Long friendId) {
        this.userStorage.addFriend(userId, friendId);
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
     */
    public void removeFriend(Long userId, Long friendId) {
        this.userStorage.removeFriend(userId, friendId);
    }
}
