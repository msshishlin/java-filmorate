package ru.yandex.practicum.filmorate.storage.abstractions;

import ru.yandex.practicum.filmorate.model.MotionPictureAssociation;

import java.util.Collection;
import java.util.Optional;

/**
 * Контракт хранилища оценок Ассоциации кинокомпаний.
 */
public interface MotionPictureAssociationStorage {
    /**
     * Получить список всех оценок Ассоциации кинокомпаний.
     *
     * @return список всех оценок Ассоциации кинокомпаний.
     */
    Collection<MotionPictureAssociation> getAll();

    /**
     * Получить оценку Ассоциации кинокомпаний по её идентификатору.
     *
     * @param mpaId идентификатор оценки Ассоциации кинокомпаний.
     * @return оценка Ассоциации кинокомпаний.
     */
    Optional<MotionPictureAssociation> getMpaById(long mpaId);

    /**
     * Получить оценку Ассоциации кинокомпаний по идентификатору фильма.
     *
     * @param filmId идентификатор фильма.
     * @return оценка Ассоциации кинокомпаний.
     */
    Optional<MotionPictureAssociation> getMpaByFilmId(long filmId);
}
