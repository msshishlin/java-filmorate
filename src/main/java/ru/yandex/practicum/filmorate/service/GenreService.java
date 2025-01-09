package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.abstractions.GenreStorage;

import java.util.Collection;
import java.util.Optional;

/**
 * Сервис для работы с жанрами.
 */
@Service
public final class GenreService {
    /**
     * Хранилище жанров.
     */
    @Autowired
    @Qualifier(GenreDbStorage.CLASS_NAME)
    private GenreStorage genreStorage;


    /**
     * Получить список всех жанров.
     *
     * @return список всех жанров.
     */
    public Collection<Genre> getAll() {
        return this.genreStorage.getAll();
    }

    /**
     * Получить жанр по его идентификатор.
     *
     * @param genreId идентификатор жанра.
     * @return жанр.
     */
    public Genre getGenreById(long genreId) {
        Optional<Genre> genre = this.genreStorage.getGenreById(genreId);
        if (genre.isEmpty()) {
            throw new NotFoundException(String.format("Жанр с идентификатором %d не найден", genreId));
        }

        return genre.get();
    }
}
