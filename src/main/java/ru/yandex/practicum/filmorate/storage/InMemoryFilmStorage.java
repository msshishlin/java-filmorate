package ru.yandex.practicum.filmorate.storage;

// region imports

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.abstractions.FilmStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// endregion

/**
 * Хранилище фильмов в оперативной памяти компьютера.
 */
@Component
public class InMemoryFilmStorage implements FilmStorage {
    /**
     * Список фильмов.
     */
    private final Map<Long, Film> films;

    /**
     * Конструктор.
     */
    public InMemoryFilmStorage() {
        this.films = new HashMap<>();
    }

    /**
     * Создать фильм.
     *
     * @param film фильм.
     * @return созданный фильм.
     */
    @Override
    public Film create(Film film) {
        film.setId(this.getNextId());

        this.films.put(film.getId(), film);
        return film;
    }

    /**
     * Получить список всех фильмов.
     *
     * @return список всех фильмов.
     */
    @Override
    public Collection<Film> getAll() {
        return this.films.values();
    }

    /**
     * Получить фильм по его идентификатору.
     *
     * @param filmId идентификатор фильма.
     * @return фильм.
     */
    @Override
    public Optional<Film> getFilmById(long filmId) {
        return Optional.ofNullable(this.films.get(filmId));
    }

    /**
     * Обновить фильм.
     *
     * @param film фильм.
     * @return обновленный фильм.
     */
    @Override
    public Film update(Film film) {
        Film oldFilm = this.films.get(film.getId());
        if (oldFilm == null) {
            throw new NotFoundException(String.format("Фильм с идентификатором %d не найден", film.getId()));
        }

        oldFilm.setDescription(film.getDescription());
        oldFilm.setDuration(film.getDuration());
        oldFilm.setName(film.getName());
        oldFilm.setReleaseDate(film.getReleaseDate());

        return oldFilm;
    }

    /**
     * Поставить фильму пользовательский лайк.
     *
     * @param filmId идентификатор фильма.
     * @param userId идентификатор пользователя.
     */
    @Override
    public void addLike(Long filmId, Long userId) {
        Film film = this.films.get(filmId);
        if (film == null) {
            throw new NotFoundException(String.format("Фильм с идентификатором %d не найден", filmId));
        }

        film.getUsersLikes().add(userId);
    }

    /**
     * Удалить у фильма пользовательский лайк.
     *
     * @param filmId идентификатор фильма.
     * @param userId идентификатор пользователя.
     */
    @Override
    public void removeLike(Long filmId, Long userId) {
        Film film = this.films.get(filmId);
        if (film == null) {
            throw new NotFoundException(String.format("Фильм с идентификатором %d не найден", filmId));
        }

        film.getUsersLikes().remove(userId);
    }

    /**
     * Получить {@code count} популярных фильмов.
     *
     * @param count количество фильмов.
     * @return {@code count} популярных фильмов.
     */
    @Override
    public Collection<Film> getPopularFilms(Long count) {
        return this.films.values()
                .stream()
                .sorted((f1, f2) -> Integer.compare(f2.getUsersLikes().size(), f1.getUsersLikes().size()))
                .limit(count)
                .toList();
    }

    // region Facilities

    /**
     * Получить идентификатор для создания нового фильма.
     *
     * @return идентификатор для создания нового фильма.
     */
    private long getNextId() {
        long currentMaxId = this.films.values()
                .stream()
                .mapToLong(Film::getId)
                .max()
                .orElse(0);

        return ++currentMaxId;
    }

    // endregion
}
