package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MotionPictureAssociation;
import ru.yandex.practicum.filmorate.storage.abstractions.MotionPictureAssociationStorage;

import java.util.Collection;
import java.util.Optional;

/**
 * Хранилище оценок Ассоциации кинокомпаний в БД.
 */
@Component(MotionPictureAssociationDbStorage.CLASS_NAME)
public final class MotionPictureAssociationDbStorage extends BaseDbStorage<MotionPictureAssociation> implements MotionPictureAssociationStorage {
    /**
     * Название текущего класса.
     */
    public static final String CLASS_NAME = "MotionPictureAssociationDbStorage";

    /**
     * SQL-запрос для получения списка всех оценок Ассоциации кинокомпаний.
     */
    private static final String GET_ALL_MPA_QUERY = "SELECT * FROM mpa";

    /**
     * SQL-запрос для получения оценки Ассоциации кинокомпаний по её идентификатору.
     */
    private static final String GET_MPA_BY_ID_QUERY = "SELECT * FROM mpa WHERE id = ?";

    /**
     * SQL-запрос для получения оценки Ассоциации кинокомпаний по идентификатору фильма.
     */
    private static final String GET_MPA_BY_FILM_ID_QUERY = "SELECT m.* FROM mpa m JOIN films f ON f.mpa_id = m.id WHERE f.id = ?";

    /**
     * Конструктор.
     *
     * @param jdbcTemplate
     * @param rowMapper
     */
    public MotionPictureAssociationDbStorage(JdbcTemplate jdbcTemplate, RowMapper<MotionPictureAssociation> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    /**
     * Получить список всех оценок Ассоциации кинокомпаний.
     *
     * @return список всех оценок Ассоциации кинокомпаний.
     */
    @Override
    public Collection<MotionPictureAssociation> getAll() {
        return this.findMany(GET_ALL_MPA_QUERY);
    }

    /**
     * Получить оценку Ассоциации кинокомпаний по её идентификатору.
     *
     * @param mpaId идентификатор оценки Ассоциации кинокомпаний.
     * @return оценка Ассоциации кинокомпаний.
     */
    @Override
    public Optional<MotionPictureAssociation> getMpaById(long mpaId) {
        return this.findOne(GET_MPA_BY_ID_QUERY, mpaId);
    }

    /**
     * Получить оценку Ассоциации кинокомпаний по идентификатору фильма.
     *
     * @param filmId идентификатор фильма.
     * @return оценка Ассоциации кинокомпаний.
     */
    @Override
    public Optional<MotionPictureAssociation> getMpaByFilmId(long filmId) {
        return this.findOne(GET_MPA_BY_FILM_ID_QUERY, filmId);
    }
}
