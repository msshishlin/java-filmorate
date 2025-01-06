package ru.yandex.practicum.filmorate.storage;

// region imports

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.UpdateEntityException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.abstractions.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

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
    @Override
    public User create(User user) {
        this.users.put(user.getId(), user);
        return user;
    }

    /**
     * Получить список всех пользователей.
     *
     * @return список всех пользователей.
     */
    @Override
    public Collection<User> getAll() {
        return this.users.values();
    }

    /**
     * Найти пользователя по его идентификатору.
     *
     * @param userId идентификатор пользователя.
     * @return пользователь.
     */
    @Override
    public Optional<User> findById(Long userId) {
        return Optional.ofNullable(this.users.get(userId));
    }

    /**
     * Обновить пользователя.
     *
     * @param user пользователь.
     * @return обновленный пользователь.
     */
    @Override
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

    /**
     * Добавить в друзья.
     *
     * @param userId   идентификатор пользователя.
     * @param friendId идентификатор друга.
     * @return пользователь.
     */
    @Override
    public User addFriend(Long userId, Long friendId) {
        User user = this.users.get(userId);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден", userId));
        }

        User friend = this.users.get(friendId);
        if (friend == null) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден", friendId));
        }

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);

        return user;
    }

    /**
     * Получить друзей пользователя.
     *
     * @param userId идентификатор пользователя.
     * @return список друзей пользователя.
     */
    @Override
    public Collection<User> getFriends(Long userId) {
        User user = this.users.get(userId);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден", userId));
        }

        Set<Long> friends = user.getFriends();

        return this.users.values()
                .stream()
                .filter(u -> friends.contains(u.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Получить общих друзей двух пользователей.
     *
     * @param userId      идентификатор пользователя.
     * @param otherUserId идентификатор другого пользователя.
     * @return список общих друзей двух пользователей.
     */
    @Override
    public Collection<User> getCommonFriends(Long userId, Long otherUserId) {
        User user = this.users.get(userId);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден", userId));
        }

        User otherUser = this.users.get(otherUserId);
        if (otherUser == null) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден", otherUserId));
        }

        Set<Long> userFriends = user.getFriends();
        Set<Long> otherUserFriend = otherUser.getFriends();

        return this.users.values()
                .stream()
                .filter(u -> userFriends.contains(u.getId()) && otherUserFriend.contains(u.getId()))
                .toList();
    }

    /**
     * Удалить из друзей.
     *
     * @param userId   идентификатор пользователя.
     * @param friendId идентификатор друга.
     * @return пользователь.
     */
    @Override
    public User removeFriend(Long userId, Long friendId) {
        User user = this.users.get(userId);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден", userId));
        }

        User friend = this.users.get(friendId);
        if (friend == null) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден", friendId));
        }

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);

        return user;
    }
}
