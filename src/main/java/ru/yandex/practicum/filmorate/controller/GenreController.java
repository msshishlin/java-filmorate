package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

/**
 * Контроллер, обрабатывающий запросы жанров фильмов.
 */
@RequestMapping("/genres")
@RestController
public class GenreController {
    /**
     * Сервис для работы с жанрами.
     */
    @Autowired
    private GenreService genreService;

    /**
     * Получить список всех жанров.
     *
     * @return список всех жанров.
     */
    @GetMapping
    public Collection<GenreDto> getAll() {
        return GenreMapper.mapToGenreDtoCollection(this.genreService.getAll());
    }

    /**
     * Получить жанр по его идентификатору.
     *
     * @param genreId идентификатор жанра.
     * @return жанр.
     */
    @GetMapping("/{genreId}")
    public GenreDto getGenreById(@PathVariable long genreId) {
        return GenreMapper.mapToGenreDto(this.genreService.getGenreById(genreId));
    }
}
