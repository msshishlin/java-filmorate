package ru.yandex.practicum.filmorate.model;

// region imports

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.film.constraint.DurationConstraint;
import ru.yandex.practicum.filmorate.validation.film.constraint.ReleaseDateConstraint;

import java.time.Duration;
import java.time.LocalDate;

// endregion

/**
 * Фильм.
 */
@AllArgsConstructor
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
}
