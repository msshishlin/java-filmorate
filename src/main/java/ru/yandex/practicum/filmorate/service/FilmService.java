package ru.yandex.practicum.filmorate.service;

// region imports

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.abstractions.FilmStorage;
import ru.yandex.practicum.filmorate.storage.abstractions.UserStorage;

import java.util.Collection;
import java.util.Optional;

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
     * Хранилище пользователей.
     */
    private final UserStorage userStorage;

    /**
     * Конструктор.
     *
     * @param filmStorage хранилище фильмов.
     */
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
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

    /**
     * Поставить фильму пользовательский лайк.
     *
     * @param filmId идентификатор фильма.
     * @param userId идентификатор пользователя.
     * @return фильм.
     */
    public Film addLike(Long filmId, Long userId) {
        Optional<User> user = this.userStorage.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден", userId));
        }

        return this.filmStorage.addLike(filmId, userId);
    }

    /**
     * Удалить у фильма пользовательский лайк.
     *
     * @param filmId идентификатор фильма.
     * @param userId идентификатор пользователя.
     * @return фильм.
     */
    public Film removeLike(Long filmId, Long userId) {
        Optional<User> user = this.userStorage.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден", userId));
        }

        return this.filmStorage.removeLike(filmId, userId);
    }

    /**
     * Получить {@code count} популярных фильмов.
     *
     * @param count количество фильмов.
     * @return {@code count} популярных фильмов.
     */
    public Collection<Film> getPopularFilms(Long count) {
        return this.filmStorage.getPopularFilms(count);
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
