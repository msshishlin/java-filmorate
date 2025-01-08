package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private String name;

    /**
     * Описание фильма.
     */
    private String description;

    /**
     * Дата релиза.
     */
    private LocalDate releaseDate;

    /**
     * Продолжительность фильма.
     */
    private Duration duration;

    /**
     * Рейтинг Ассоциации кинокомпаний.
     */
    private MotionPictureAssociation mpa;

    /**
     * Жанры, к которым относится фильм.
     */
    private Set<Genre> genres = new HashSet<>();

    /**
     * Пользовательские лайки.
     */
    private Set<Long> usersLikes = new HashSet<>();
}
