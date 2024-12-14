package ru.yandex.practicum.filmorate.storage.abstractions;

// region imports

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

// endregion

/**
 * Контракт хранилища фильмов.
 */
public interface FilmStorage {
    /**
     * Создать фильм.
     *
     * @param film фильм.
     * @return созданный фильм.
     */
    Film create(Film film);

    /**
     * Получить список всех фильмов.
     *
     * @return список всех фильмов.
     */
    Collection<Film> getAll();

    /**
     * Обновить фильм.
     *
     * @param film фильм.
     * @return обновленный фильм.
     */
    Film update(Film film);
}
