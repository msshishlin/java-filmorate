package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.abstractions.FilmStorage;

import java.util.Collection;
import java.util.Optional;

/**
 * Хранилище фильмов в БД.
 */
@Component(FilmDbStorage.CLASS_NAME)
public class FilmDbStorage extends BaseDbStorage<Film> implements FilmStorage {
    /**
     * Название текущего класса.
     */
    public static final String CLASS_NAME = "FilmDbStorage";

    /**
     * SQL-запрос для создания нового фильма.
     */
    private static final String CREATE_FILM_QUERY = "INSERT INTO films (name, description, release_date, duration, mpa_id) VALUES (?, ?, ?, ?, ?)";

    /**
     * SQL-запрос для получения всех фильмов.
     */
    private static final String GET_ALL_FILMS_QUERY = "SELECT * FROM films";

    /**
     * SQL-запрос для получения фильма по его идентификатору.
     */
    private static final String GET_FILM_BY_ID_QUERY = "SELECT * FROM films WHERE id = ?";

    /**
     * SQL-запрос для получения списка популярных фильмов.
     */
    private static final String GET_POPULAR_FILMS_QUERY = "SELECT f.*, COUNT(fl.id) as likes_count FROM films f LEFT JOIN film_likes fl ON fl.film_id = f.id GROUP BY f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id ORDER BY likes_count DESC LIMIT ?";

    /**
     * SQL-запрос для обновления фильма.
     */
    private static final String UPDATE_FILM_QUERY = "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? WHERE id = ?";

    /**
     * SQL-запрос для добавления жанра для фильма.
     */
    private static final String ADD_GENRE_TO_FILM_QUERY = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";

    /**
     * SQL-запрос для удаления жанров фильма.
     */
    private static final String DELETE_GENRES_FROM_FILM_QUERY = "DELETE FROM film_genres WHERE film_id = ?";

    /**
     * SQL-запрос для добавления фильму лайка от пользователя.
     */
    private static final String ADD_LIKE_TO_FILM_QUERY = "INSERT INTO film_likes (film_id, user_id) VALUES (?, ?)";

    /**
     * SQL-запрос для удаления лайка от пользователя.
     */
    private static final String REMOVE_LIKE_FROM_FILM_QUERY = "DELETE FROM film_likes WHERE film_id = ? AND user_id = ?";

    /**
     * Конструктор.
     *
     * @param jdbcTemplate
     * @param rowMapper
     */
    public FilmDbStorage(JdbcTemplate jdbcTemplate, RowMapper<Film> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    /**
     * Создать фильм.
     *
     * @param film фильм.
     * @return созданный фильм.
     */
    @Override
    public Film create(Film film) {
        long id = this.insert(CREATE_FILM_QUERY, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration().getSeconds(), film.getMpa().getId());
        film.getGenres().forEach(g -> this.insert(ADD_GENRE_TO_FILM_QUERY, id, g.getId()));

        film.setId(id);
        return film;
    }

    /**
     * Получить список всех фильмов.
     *
     * @return список всех фильмов.
     */
    @Override
    public Collection<Film> getAll() {
        return this.findMany(GET_ALL_FILMS_QUERY);
    }

    /**
     * Получить фильм по его идентификатору.
     *
     * @param filmId идентификатор фильма.
     * @return фильм.
     */
    @Override
    public Optional<Film> getFilmById(long filmId) {
        return this.findOne(GET_FILM_BY_ID_QUERY, filmId);
    }

    /**
     * Обновить фильм.
     *
     * @param film фильм.
     * @return обновленный фильм.
     */
    @Override
    public Film update(Film film) {
        Optional<Film> oldFilm = this.findOne(GET_FILM_BY_ID_QUERY, film.getId());
        if (oldFilm.isEmpty()) {
            throw new NotFoundException(String.format("Фильм с идентификатором %d не найден", film.getId()));
        }

        this.update(UPDATE_FILM_QUERY, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration().getSeconds(), film.getMpa().getId(), film.getId());

        this.delete(DELETE_GENRES_FROM_FILM_QUERY, film.getId());
        film.getGenres().forEach(g -> this.insert(ADD_GENRE_TO_FILM_QUERY, film.getId(), g.getId()));

        return film;
    }

    /**
     * Поставить фильму пользовательский лайк.
     *
     * @param filmId идентификатор фильма.
     * @param userId идентификатор пользователя.
     */
    @Override
    public void addLike(Long filmId, Long userId) {
        Optional<Film> oldFilm = this.findOne(GET_FILM_BY_ID_QUERY, filmId);
        if (oldFilm.isEmpty()) {
            throw new NotFoundException(String.format("Фильм с идентификатором %d не найден", filmId));
        }

        this.insert(ADD_LIKE_TO_FILM_QUERY, filmId, userId);
    }

    /**
     * Удалить у фильма пользовательский лайк.
     *
     * @param filmId идентификатор фильма.
     * @param userId идентификатор пользователя.
     */
    @Override
    public void removeLike(Long filmId, Long userId) {
        Optional<Film> oldFilm = this.findOne(GET_FILM_BY_ID_QUERY, filmId);
        if (oldFilm.isEmpty()) {
            throw new NotFoundException(String.format("Фильм с идентификатором %d не найден", filmId));
        }

        this.delete(REMOVE_LIKE_FROM_FILM_QUERY, filmId, userId);
    }

    /**
     * Получить {@code count} популярных фильмов.
     *
     * @param count количество фильмов.
     * @return {@code count} популярных фильмов.
     */
    @Override
    public Collection<Film> getPopularFilms(Long count) {
        return this.findMany(GET_POPULAR_FILMS_QUERY, count);
    }
}
