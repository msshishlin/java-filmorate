package ru.yandex.practicum.filmorate.model;

// region imports

import lombok.Data;

// endregion

/**
 * Жанр фильма.
 */
@Data
public class Genre {
    /**
     * Идентификатор жанра.
     */
    private Long id;

    /**
     * Название жанра.
     */
    private String name;
}
