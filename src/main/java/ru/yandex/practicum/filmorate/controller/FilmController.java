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
}
