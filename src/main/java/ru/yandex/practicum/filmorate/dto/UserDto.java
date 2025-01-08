package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Трансферный объект для сущности "Пользователь".
 */
@Data
public final class UserDto {
    /**
     * Идентификатор пользователя.
     */
    private Long id;

    /**
     * Логин пользователя.
     */
    @NotBlank(message = "Логин пользователя не может быть пустым")
    @Pattern(regexp = "^\\S+$", message = "Логин пользователя не может содержать пробелы")
    private String login;

    /**
     * Электронная почта пользователя.
     */
    @Email(message = "Адрес электронной почты должен содержать символ '@'")
    @NotEmpty(message = "Адрес электронной почты пользователя не может быть пустым")
    private String email;

    /**
     * Имя пользователя для отображения.
     */
    private String name;

    /**
     * Дата рождения пользователя.
     */
    @PastOrPresent(message = "Дата рождения пользователя не может быть больше текущей даты")
    private LocalDate birthday;
}
