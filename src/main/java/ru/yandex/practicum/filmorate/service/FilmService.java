package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MotionPictureAssociation;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.MotionPictureAssociationDbStorage;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.abstractions.FilmStorage;
import ru.yandex.practicum.filmorate.storage.abstractions.GenreStorage;
import ru.yandex.practicum.filmorate.storage.abstractions.MotionPictureAssociationStorage;
import ru.yandex.practicum.filmorate.storage.abstractions.UserStorage;

import java.util.*;

/**
 * Сервис для работы с фильмами.
 */
@Service
public class FilmService {
    /**
     * Хранилище фильмов.
     */
    @Autowired
    @Qualifier(FilmDbStorage.CLASS_NAME)
    private FilmStorage filmStorage;

    /**
     * Хранилище жанров.
     */
    @Autowired
    @Qualifier(GenreDbStorage.CLASS_NAME)
    private GenreStorage genreStorage;

    /**
     * Хранилище оценок Ассоциации кинокомпаний.
     */
    @Autowired
    @Qualifier(MotionPictureAssociationDbStorage.CLASS_NAME)
    private MotionPictureAssociationStorage mpaStorage;

    /**
     * Хранилище пользователей.
     */
    @Autowired
    @Qualifier(UserDbStorage.CLASS_NAME)
    private UserStorage userStorage;

    /**
     * Создать фильм.
     *
     * @param film фильм.
     * @return созданный фильм.
     */
    public Film create(Film film) {
        Optional<MotionPictureAssociation> mpa = this.mpaStorage.getMpaById(film.getMpa().getId());
        if (mpa.isEmpty()) {
            throw new NotFoundException(String.format("Оценка Ассоциации кинокомпаний с идентификатором %d не найдена", film.getMpa().getId()));
        }
        film.setMpa(mpa.get());

        Collection<Genre> genres = film.getGenres().stream().map(g -> {
            Optional<Genre> genre = this.genreStorage.getGenreById(g.getId());
            if (genre.isEmpty()) {
                throw new NotFoundException(String.format("Жанр с идентификатором %d не найден", g.getId()));
            }

            return genre.get();
        }).toList();
        film.setGenres(genres);

        return this.filmStorage.create(film);
    }

    /**
     * Получить список всех фильмов.
     *
     * @return список всех фильмов.
     */
    public Collection<Film> getAll() {
        Collection<Film> films = this.filmStorage.getAll();

        films.forEach(f -> {
            Optional<MotionPictureAssociation> mpa = this.mpaStorage.getMpaByFilmId(f.getId());
            if (mpa.isEmpty()) {
                throw new NotFoundException(String.format("Оценка Ассоциации кинокомпаний для фильма с идентификатором %d не найдена", f.getId()));
            }

            f.setMpa(mpa.get());
            f.setGenres(this.genreStorage.getGenresByFilmId(f.getId()));
        });

        return films;
    }

    /**
     * Получить фильм по его идентификатору.
     *
     * @param filmId идентификатор фильма.
     * @return фильм.
     */
    public Film getFilmById(long filmId) {
        Optional<Film> optionalFilm = this.filmStorage.getFilmById(filmId);
        if (optionalFilm.isEmpty()) {
            throw new NotFoundException(String.format("Фильм с идентификатором %d не найден", filmId));
        }

        Film film = optionalFilm.get();

        Optional<MotionPictureAssociation> mpa = this.mpaStorage.getMpaByFilmId(filmId);
        if (mpa.isEmpty()) {
            throw new NotFoundException(String.format("Оценка Ассоциации кинокомпаний для фильма с идентификатором %d не найдена", filmId));
        }

        film.setMpa(mpa.get());
        film.setGenres(this.genreStorage.getGenresByFilmId(filmId));

        return film;
    }

    /**
     * Обновить фильм.
     *
     * @param film фильм.
     * @return обновленный фильм.
     */
    public Film update(Film film) {
        Optional<MotionPictureAssociation> mpa = this.mpaStorage.getMpaById(film.getMpa().getId());
        if (mpa.isEmpty()) {
            throw new NotFoundException(String.format("Оценка Ассоциации кинокомпаний с идентификатором %d не найдена", film.getMpa().getId()));
        }
        film.setMpa(mpa.get());

        Collection<Genre> genres = film.getGenres().stream().map(g -> {
            Optional<Genre> genre = this.genreStorage.getGenreById(g.getId());
            if (genre.isEmpty()) {
                throw new NotFoundException(String.format("Жанр с идентификатором %d не найден", g.getId()));
            }

            return genre.get();
        }).toList();
        film.setGenres(genres);

        return this.filmStorage.update(film);
    }

    /**
     * Поставить фильму пользовательский лайк.
     *
     * @param filmId идентификатор фильма.
     * @param userId идентификатор пользователя.
     */
    public void addLike(Long filmId, Long userId) {
        Optional<User> user = this.userStorage.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден", userId));
        }

        this.filmStorage.addLike(filmId, userId);
    }

    /**
     * Удалить у фильма пользовательский лайк.
     *
     * @param filmId идентификатор фильма.
     * @param userId идентификатор пользователя.
     */
    public void removeLike(Long filmId, Long userId) {
        Optional<User> user = this.userStorage.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден", userId));
        }

        this.filmStorage.removeLike(filmId, userId);
    }

    /**
     * Получить {@code count} популярных фильмов.
     *
     * @param count количество фильмов.
     * @return {@code count} популярных фильмов.
     */
    public Collection<Film> getPopularFilms(Long count) {
        Collection<Film> films = this.filmStorage.getPopularFilms(count);

        films.forEach(f -> {
            Optional<MotionPictureAssociation> mpa = this.mpaStorage.getMpaByFilmId(f.getId());
            if (mpa.isEmpty()) {
                throw new NotFoundException(String.format("Оценка Ассоциации кинокомпаний для фильма с идентификатором %d не найдена", f.getId()));
            }

            f.setMpa(mpa.get());
            f.setGenres(this.genreStorage.getGenresByFilmId(f.getId()));
        });

        return films;
    }
}
