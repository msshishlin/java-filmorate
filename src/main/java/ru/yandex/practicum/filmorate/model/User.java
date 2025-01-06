package ru.yandex.practicum.filmorate.model;

// region imports

import jakarta.validation.constraints.*;
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
     * Электронная почта пользователя.
     */
    @Email(message = "Адрес электронной почты должен содержать символ '@'")
    @NotEmpty(message = "Адрес электронной почты пользователя не может быть пустым")
    private String email;

    /**
     * Логин пользователя.
     */
    @NotBlank(message = "Логин пользователя не может быть пустым")
    @Pattern(regexp = "^\\S+$", message = "Логин пользователя не может содержать пробелы")
    private String login;

    /**
     * Имя пользователя для отображения.
     */
    private String name;

    /**
     * Дата рождения пользователя.
     */
    @PastOrPresent(message = "Дата рождения пользователя не может быть больше текущей даты")
    private LocalDate birthday;

    /**
     * Список друзей.
     */
    private Set<Long> friends = new HashSet<>();
}
