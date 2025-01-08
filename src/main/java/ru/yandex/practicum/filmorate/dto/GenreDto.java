package ru.yandex.practicum.filmorate.dto;

import lombok.Data;

/**
 * Трансферный объект для сущности "Жанр фильма".
 */
@Data
public final class GenreDto {
    /**
     * Идентификатор жанра.
     */
    private Long id;

    /**
     * Название жанра.
     */
    private String name;
}
