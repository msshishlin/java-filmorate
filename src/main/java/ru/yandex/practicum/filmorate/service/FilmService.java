package ru.yandex.practicum.filmorate.service;

// region imports

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.abstractions.FilmStorage;

import java.util.Collection;

// endregion

/**
 * Сервис для работы с фильмами.
 */
@Service
public class FilmService {
    /**
     * Хранилище фильмов.
     */
    private final FilmStorage filmStorage;

    /**
     * Конструктор.
     *
     * @param filmStorage хранилище фильмов.
     */
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    /**
     * Создать фильм.
     *
     * @param film фильм.
     * @return созданный фильм.
     */
    public Film create(Film film) {
        film.setId(this.getNextId());
        return this.filmStorage.create(film);
    }

    /**
     * Получить список всех фильмов.
     *
     * @return список всех фильмов.
     */
    public Collection<Film> getAll() {
        return this.filmStorage.getAll();
    }

    /**
     * Обновить фильм.
     *
     * @param film фильм.
     * @return обновленный фильм.
     */
    public Film update(Film film) {
        return this.filmStorage.update(film);
    }

    // region Facilities

    /**
     * Получить идентификатор для создания нового фильма.
     *
     * @return идентификатор для создания нового фильма.
     */
    private long getNextId() {
        long currentMaxId = this.filmStorage.getAll()
                .stream()
                .mapToLong(Film::getId)
                .max()
                .orElse(0);

        return ++currentMaxId;
    }

    // endregion
}
