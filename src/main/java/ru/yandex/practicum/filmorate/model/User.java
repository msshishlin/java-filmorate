package ru.yandex.practicum.filmorate.model;

// region imports

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

// endregion

/**
 * Пользователь.
 */
@Data
public class User {
    /**
     * Идентификатор пользователя.
     */
    private Long id;

    /**
     * Логин пользователя.
     */
    private String login;

    /**
     * Электронная почта пользователя.
     */
    private String email;

    /**
     * Имя пользователя для отображения.
     */
    private String name;

    /**
     * Дата рождения пользователя.
     */
    private LocalDate birthday;

    /**
     * Список друзей.
     */
    private Set<Long> friends = new HashSet<>();
}
