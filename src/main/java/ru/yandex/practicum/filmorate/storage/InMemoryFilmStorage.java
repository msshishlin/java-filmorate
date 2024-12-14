package ru.yandex.practicum.filmorate.storage;

// region imports

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.abstractions.FilmStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
    public Film create(Film film) {
        this.films.put(film.getId(), film);
        return film;
    }

    /**
     * Получить список всех фильмов.
     *
     * @return список всех фильмов.
     */
    public Collection<Film> getAll() {
        return this.films.values();
    }

    /**
     * Обновить фильм.
     *
     * @param film фильм.
     * @return обновленный фильм.
     */
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
}
