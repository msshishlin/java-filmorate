package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.abstractions.GenreStorage;

import java.util.Collection;
import java.util.Optional;

/**
 * Хранилище жанров.
 */
@Component(GenreDbStorage.CLASS_NAME)
public class GenreDbStorage extends BaseDbStorage<Genre> implements GenreStorage {
    /**
     * Название текущего класса.
     */
    public static final String CLASS_NAME = "GenreDbStorage";

    /**
     * SQL-запрос для получения списка всех жанров.
     */
    private static final String GET_ALL_GENRES_QUERY = "SELECT * FROM genres";

    /**
     * SQL-запрос для поиска жанра по его идентификатору.
     */
    private static final String GET_GENRE_BY_ID_QUERY = "SELECT * FROM genres WHERE id = ?";

    /**
     * SQL-запрос для получения жанров фильма.
     */
    private static final String GET_GENRES_BY_FILM_ID_QUERY = "SELECT g.* FROM genres g JOIN film_genres fg ON fg.genre_id = g.id WHERE fg.film_id = ?";

    /**
     * Конструктор.
     *
     * @param jdbcTemplate
     * @param rowMapper
     */
    public GenreDbStorage(JdbcTemplate jdbcTemplate, RowMapper<Genre> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    /**
     * Получить список всех жанров.
     *
     * @return список всех жанров.
     */
    @Override
    public Collection<Genre> getAll() {
        return this.findMany(GET_ALL_GENRES_QUERY);
    }

    /**
     * Получить жанр по его идентификатор.
     *
     * @param genreId идентификатор жанра.
     * @return жанр.
     */
    @Override
    public Optional<Genre> getGenreById(long genreId) {
        return this.findOne(GET_GENRE_BY_ID_QUERY, genreId);
    }

    /**
     * Получить список жанров по идентификатору фильма.
     *
     * @param filmId идентификатор фильма.
     * @return список жанров.
     */
    @Override
    public Collection<Genre> getGenresByFilmId(long filmId) {
        return this.findMany(GET_GENRES_BY_FILM_ID_QUERY, filmId);
    }
}
