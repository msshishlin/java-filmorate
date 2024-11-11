package ru.yandex.practicum.filmorate.controller;

// region imports

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// endregion

/**
 * Контроллер, обрабатывающий запросы фильмов.
 */
@RequestMapping("/films")
@RestController
@Slf4j
public class FilmController {
    /**
     * Список фильмов.
     */
    private final Map<Long, Film> films;

    /**
     * Конструктор.
     */
    public FilmController() {
        this.films = new HashMap<>();
    }

    /**
     * Создать фильм.
     *
     * @param film фильм.
     * @return созданный фильм.
     */
    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        try {
            this.validateBeforeCreate(film);
        } catch (ValidationException ex) {
            log.error(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        }

        log.debug("Creating film {}", film);

        film.setId(this.getNextId());
        log.trace("Calculated film id: {}", film.getId());

        this.films.put(film.getId(), film);
        return film;
    }

    /**
     * Получить коллекцию всех фильмов.
     *
     * @return коллекция фильмов.
     */
    @GetMapping
    public Collection<Film> getAll() {
        return films.values();
    }

    /**
     * Обновить фильм.
     *
     * @param newFilm фильм с измененными значениями полей.
     * @return обновленный фильм.
     */
    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        try {
            this.validateBeforeUpdate(newFilm);
        } catch (ValidationException ex) {
            log.error(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        }

        if (!this.films.containsKey(newFilm.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Фильм с идентификатором %d не найден", newFilm.getId()));
        }

        log.debug("Updating film {}", newFilm);

        Film oldFilm = this.films.get(newFilm.getId());
        oldFilm.setName(newFilm.getName());
        oldFilm.setDescription(newFilm.getDescription());
        oldFilm.setReleaseDate(newFilm.getReleaseDate());
        oldFilm.setDuration(newFilm.getDuration());

        log.debug("Updated film {}", oldFilm);
        return oldFilm;
    }

    // region Facilities

    /**
     * Валидация фильма перед его созданием.
     *
     * @param film фильм.
     */
    private void validateBeforeCreate(Film film) {
        if (film.getReleaseDate() != null && film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }

        if (film.getDuration() != null && !film.getDuration().isPositive()) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
    }

    /**
     * Валидация фильма перед его обновлением.
     *
     * @param film фильм.
     */
    private void validateBeforeUpdate(Film film) {
        if (film.getId() == null) {
            throw new ValidationException("Не задан идентификатор фильма");
        }

        this.validateBeforeCreate(film);
    }

    // вспомогательный метод для генерации идентификатора нового поста
    private long getNextId() {
        long currentMaxId = this.films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);

        return ++currentMaxId;
    }

    // endregion
}
