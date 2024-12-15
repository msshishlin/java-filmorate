package ru.yandex.practicum.filmorate.controller;

// region imports

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.MissedEntityIdException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

// endregion

/**
 * Контроллер, обрабатывающий запросы фильмов.
 */
@RequestMapping("/films")
@RestController
public class FilmController {
    /**
     * Сервис для работы с фильмами.
     */
    private final FilmService filmService;

    /**
     * Конструктор.
     */
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    /**
     * Создать фильм.
     *
     * @param film   фильм.
     * @param result результат привязки тела запроса к полям модели.
     * @return созданный фильм.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film create(@RequestBody @Valid Film film, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result.getAllErrors());
        }

        return this.filmService.create(film);
    }

    /**
     * Получить коллекцию всех фильмов.
     *
     * @return коллекция фильмов.
     */
    @GetMapping
    public Collection<Film> getAll() {
        return this.filmService.getAll();
    }

    /**
     * Обновить фильм.
     *
     * @param film   фильм.
     * @param result результат привязки тела запроса к полям модели.
     * @return обновленный фильм.
     */
    @PutMapping
    public Film update(@RequestBody @Valid Film film, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result.getAllErrors());
        }

        if (film.getId() == null) {
            throw new MissedEntityIdException("Не задан идентификатор фильма");
        }

        return this.filmService.update(film);
    }

    /**
     * Поставить фильму пользовательский лайк.
     *
     * @param filmId идентификатор фильма.
     * @param userId идентификатор пользователя.
     * @return фильм.
     */
    @PutMapping("/{filmId}/like/{userId}")
    public Film addLike(@PathVariable Long filmId, @PathVariable Long userId) {
        return this.filmService.addLike(filmId, userId);
    }

    /**
     * Удалить у фильма пользовательский лайк.
     *
     * @param filmId идентификатор фильма.
     * @param userId идентификатор пользователя.
     * @return фильм.
     */
    @DeleteMapping("/{filmId}/like/{userId}")
    public Film removeLike(@PathVariable Long filmId, @PathVariable Long userId) {
        return this.filmService.removeLike(filmId, userId);
    }

    /**
     * Получить {@code count} популярных фильмов.
     *
     * @param count количество фильмов.
     * @return {@code count} популярных фильмов.
     */
    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") Long count) {
        return this.filmService.getPopularFilms(count);
    }
}
