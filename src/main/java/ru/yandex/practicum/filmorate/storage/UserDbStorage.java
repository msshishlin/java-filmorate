package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.UpdateEntityException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.abstractions.UserStorage;

import java.util.Collection;
import java.util.Optional;

/**
 * Хранилище пользователей в БД.
 */
@Component(UserDbStorage.CLASS_NAME)
public class UserDbStorage extends BaseDbStorage<User> implements UserStorage {
    /**
     * Название текущего класса.
     */
    public static final String CLASS_NAME = "UserDbStorage";

    /**
     * SQL-запрос для создания нового пользователя.
     */
    private static final String CREATE_NEW_USER_QUERY = "INSERT INTO users (login, email, name, birthday) VALUES (?, ?, ?, ?)";

    /**
     * SQL-запрос для получения списка всех пользователей.
     */
    private static final String FIND_ALL_USERS_QUERY = "SELECT * FROM users";

    /**
     * SQL-запрос для получения пользователя по его идентификатору.
     */
    private static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";

    /**
     * SQL-запрос для получения пользователя по его e-mail.
     */
    private static final String FIND_USER_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email = ?";

    /**
     * SQL-запрос для обновления пользователя.
     */
    private static final String UPDATE_USER_QUERY = "UPDATE users SET login = ?, email = ?, name = ?, birthday = ? WHERE id = ?";

    /**
     * SQL-запрос для добавления пользователя в друзья.
     */
    private static final String ADD_USER_FRIEND_QUERY = "INSERT INTO friends (left_user_id, right_user_id) VALUES (?, ?)";

    /**
     * SQL-запрос для получения всех друзей пользователя.
     */
    private static final String GET_USER_FRIENDS_QUERY = "SELECT u.* FROM friends f JOIN users u ON u.id = f.right_user_id WHERE f.left_user_id = ?";

    /**
     * SQL-запрос для удаления пользователя из друзей.
     */
    private static final String DELETE_USER_FRIEND_QUERY = "DELETE FROM friends WHERE left_user_id = ? AND right_user_id = ?";

    /**
     * Конструктор.
     *
     * @param jdbcTemplate
     * @param rowMapper
     */
    public UserDbStorage(JdbcTemplate jdbcTemplate, RowMapper<User> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    /**
     * Создать пользователя.
     *
     * @param user пользователь.
     * @return созданный пользователь.
     */
    @Override
    public User create(User user) {
        long id = this.insert(CREATE_NEW_USER_QUERY, user.getLogin(), user.getEmail(), user.getName(), user.getBirthday());

        user.setId(id);
        return user;
    }

    /**
     * Получить список всех пользователей.
     *
     * @return список всех пользователей.
     */
    @Override
    public Collection<User> getAll() {
        return this.findMany(FIND_ALL_USERS_QUERY);
    }

    /**
     * Найти пользователя по его идентификатору.
     *
     * @param userId идентификатор пользователя.
     * @return пользователь.
     */
    @Override
    public Optional<User> findById(Long userId) {
        return this.findOne(FIND_USER_BY_ID_QUERY, userId);
    }

    /**
     * Обновить пользователя.
     *
     * @param user пользователь.
     * @return обновленный пользователь.
     */
    @Override
    public User update(User user) {
        Optional<User> oldUser = this.findOne(FIND_USER_BY_ID_QUERY, user.getId());
        if (oldUser.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден", user.getId()));
        }

        Optional<User> anotherUserWithSameEmail = this.findOne(FIND_USER_BY_EMAIL_QUERY, user.getEmail());
        if (anotherUserWithSameEmail.isPresent()) {
            throw new UpdateEntityException("Новый e-mail пользователя (" + user.getEmail() + ") уже используется");
        }

        this.update(UPDATE_USER_QUERY, user.getLogin(), user.getEmail(), user.getName(), user.getBirthday(), user.getId());
        return user;
    }

    /**
     * Добавить в друзья.
     *
     * @param userId   идентификатор пользователя.
     * @param friendId идентификатор друга.
     */
    @Override
    public void addFriend(Long userId, Long friendId) {
        Optional<User> user = this.findOne(FIND_USER_BY_ID_QUERY, userId);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден", userId));
        }

        Optional<User> friend = this.findOne(FIND_USER_BY_ID_QUERY, friendId);
        if (friend.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден", friendId));
        }

        this.insert(ADD_USER_FRIEND_QUERY, userId, friendId);
    }

    /**
     * Получить друзей пользователя.
     *
     * @param userId идентификатор пользователя.
     * @return список друзей пользователя.
     */
    @Override
    public Collection<User> getFriends(Long userId) {
        Optional<User> user = this.findOne(FIND_USER_BY_ID_QUERY, userId);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден", userId));
        }

        return this.findMany(GET_USER_FRIENDS_QUERY, userId);
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
        Collection<User> userFriends = this.getFriends(userId);
        Collection<User> otherUserFriends = this.getFriends(otherUserId);

        return userFriends.stream().filter(uf -> otherUserFriends.stream().anyMatch(ouf -> ouf.getId().equals(uf.getId()))).toList();
    }

    /**
     * Удалить из друзей.
     *
     * @param userId   идентификатор пользователя.
     * @param friendId идентификатор друга.
     */
    @Override
    public void removeFriend(Long userId, Long friendId) {
        Optional<User> user = this.findOne(FIND_USER_BY_ID_QUERY, userId);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден", userId));
        }

        Optional<User> friend = this.findOne(FIND_USER_BY_ID_QUERY, friendId);
        if (friend.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден", friendId));
        }

        this.delete(DELETE_USER_FRIEND_QUERY, userId, friendId);
    }
}
