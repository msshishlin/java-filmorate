package ru.yandex.practicum.filmorate.model;

// region imports

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.film.constraint.DurationConstraint;
import ru.yandex.practicum.filmorate.validation.film.constraint.ReleaseDateConstraint;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

// endregion

/**
 * Фильм.
 */
@Data
public class Film {
    /**
     * Идентификатор фильма.
     */
    private Long id;

    /**
     * Название фильма.
     */
    @NotBlank(message = "Название фильма не может быть пустым или состоять только из пробелов")
    private String name;

    /**
     * Описание фильма.
     */
    @NotBlank(message = "Описание фильма не может быть пустым или состоять только из пробелов")
    @Size(max = 200, message = "Максимальная длина описания фильма - 200 символов")
    private String description;

    /**
     * Дата релиза.
     */
    @ReleaseDateConstraint
    private LocalDate releaseDate;

    /**
     * Продолжительность фильма.
     */
    @DurationConstraint
    private Duration duration;

    /**
     * Пользовательские лайки.
     */
    private Set<Long> usersLikes = new HashSet<>();
}
