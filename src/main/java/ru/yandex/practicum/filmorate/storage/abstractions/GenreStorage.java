package ru.yandex.practicum.filmorate.storage.abstractions;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Optional;

/**
 * Контракт хранилища жанров.
 */
public interface GenreStorage {
    /**
     * Получить список всех жанров.
     *
     * @return список всех жанров.
     */
    Collection<Genre> getAll();

    /**
     * Получить жанр по его идентификатор.
     *
     * @param genreId идентификатор жанра.
     * @return жанр.
     */
    Optional<Genre> getGenreById(long genreId);

    /**
     * Получить список жанров по идентификатору фильма.
     *
     * @param filmId идентификатор фильма.
     * @return список жанров.
     */
    Collection<Genre> getGenresByFilmId(long filmId);
}
