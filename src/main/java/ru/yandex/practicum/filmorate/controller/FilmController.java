package ru.yandex.practicum.filmorate.controller;

// region imports

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.exception.MissedEntityIdException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
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
    @Autowired
    private FilmService filmService;

    /**
     * Создать фильм.
     *
     * @param filmDto трансферный объект для сущности "Фильм".
     * @param result  результат привязки тела запроса к полям модели.
     * @return созданный фильм.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDto create(@RequestBody @Valid FilmDto filmDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result.getAllErrors());
        }

        return FilmMapper.mapToFilmDto(this.filmService.create(FilmMapper.mapToFilm(filmDto)));
    }

    /**
     * Получить коллекцию всех фильмов.
     *
     * @return коллекция фильмов.
     */
    @GetMapping
    public Collection<FilmDto> getAll() {
        return FilmMapper.mapToFilmDtoCollection(this.filmService.getAll());
    }

    /**
     * Получить фильм по его идентификатору.
     *
     * @param filmId идентификатор фильма.
     * @return фильм.
     */
    @GetMapping("/{filmId}")
    public FilmDto getFilmById(@PathVariable long filmId) {
        return FilmMapper.mapToFilmDto(this.filmService.getFilmById(filmId));
    }

    /**
     * Обновить фильм.
     *
     * @param filmDto фильм.
     * @param result  результат привязки тела запроса к полям модели.
     * @return обновленный фильм.
     */
    @PutMapping
    public FilmDto update(@RequestBody @Valid FilmDto filmDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result.getAllErrors());
        }

        if (filmDto.getId() == null) {
            throw new MissedEntityIdException("Не задан идентификатор фильма");
        }

        return FilmMapper.mapToFilmDto(this.filmService.update(FilmMapper.mapToFilm(filmDto)));
    }

    /**
     * Поставить фильму пользовательский лайк.
     *
     * @param filmId идентификатор фильма.
     * @param userId идентификатор пользователя.
     */
    @PutMapping("/{filmId}/like/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addLike(@PathVariable Long filmId, @PathVariable Long userId) {
        this.filmService.addLike(filmId, userId);
    }

    /**
     * Удалить у фильма пользовательский лайк.
     *
     * @param filmId идентификатор фильма.
     * @param userId идентификатор пользователя.
     */
    @DeleteMapping("/{filmId}/like/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLike(@PathVariable Long filmId, @PathVariable Long userId) {
        this.filmService.removeLike(filmId, userId);
    }

    /**
     * Получить {@code count} популярных фильмов.
     *
     * @param count количество фильмов.
     * @return {@code count} популярных фильмов.
     */
    @GetMapping("/popular")
    public Collection<FilmDto> getPopularFilms(@RequestParam(defaultValue = "10") Long count) {
        return FilmMapper.mapToFilmDtoCollection(this.filmService.getPopularFilms(count));
    }
}
