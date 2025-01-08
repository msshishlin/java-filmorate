package ru.yandex.practicum.filmorate.storage.abstractions;

// region imports

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

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
     * Получить фильм по его идентификатору.
     *
     * @param filmId идентификатор фильма.
     * @return фильм.
     */
    Optional<Film> getFilmById(long filmId);

    /**
     * Обновить фильм.
     *
     * @param film фильм.
     * @return обновленный фильм.
     */
    Film update(Film film);

    /**
     * Поставить фильму пользовательский лайк.
     *
     * @param filmId идентификатор фильма.
     * @param userId идентификатор пользователя.
     */
    void addLike(Long filmId, Long userId);

    /**
     * Удалить у фильма пользовательский лайк.
     *
     * @param filmId идентификатор фильма.
     * @param userId идентификатор пользователя.
     */
    void removeLike(Long filmId, Long userId);

    /**
     * Получить {@code count} популярных фильмов.
     *
     * @param count количество фильмов.
     * @return {@code count} популярных фильмов.
     */
    Collection<Film> getPopularFilms(Long count);
}
