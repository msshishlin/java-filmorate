package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.film.constraint.DurationConstraint;
import ru.yandex.practicum.filmorate.validation.film.constraint.GenreDtoCollectionConstraint;
import ru.yandex.practicum.filmorate.validation.film.constraint.MotionPictureAssociationDtoConstraint;
import ru.yandex.practicum.filmorate.validation.film.constraint.ReleaseDateConstraint;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Трансферный объект для сущности "Фильм".
 */
@Data
public final class FilmDto {
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
     * Рейтинг Ассоциации кинокомпаний.
     */
    @MotionPictureAssociationDtoConstraint
    private MotionPictureAssociationDto mpa;

    /**
     * Жанры, к которым относится фильм.
     */
    @GenreDtoCollectionConstraint
    private Set<GenreDto> genres = new HashSet<>();
}
