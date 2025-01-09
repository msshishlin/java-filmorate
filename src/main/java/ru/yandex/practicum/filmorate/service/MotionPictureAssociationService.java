package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MotionPictureAssociation;
import ru.yandex.practicum.filmorate.storage.MotionPictureAssociationDbStorage;
import ru.yandex.practicum.filmorate.storage.abstractions.MotionPictureAssociationStorage;

import java.util.Collection;
import java.util.Optional;

/**
 * Сервис для работы с оценками Ассоциации кинокомпаний.
 */
@Service
public final class MotionPictureAssociationService {
    /**
     * Хранилище оценок Ассоциации кинокомпаний.
     */
    @Autowired
    @Qualifier(MotionPictureAssociationDbStorage.CLASS_NAME)
    private MotionPictureAssociationStorage mpaStorage;

    /**
     * Получить список всех оценок Ассоциации кинокомпаний.
     *
     * @return список всех оценок Ассоциации кинокомпаний.
     */
    public Collection<MotionPictureAssociation> getAll() {
        return this.mpaStorage.getAll();
    }

    /**
     * Получить оценку Ассоциации кинокомпаний по её идентификатору.
     *
     * @param mpaId идентификатор оценки Ассоциации кинокомпаний.
     * @return оценка Ассоциации кинокомпаний.
     */
    public MotionPictureAssociation getMpaById(long mpaId) {
        Optional<MotionPictureAssociation> mpa = this.mpaStorage.getMpaById(mpaId);
        if (mpa.isEmpty()) {
            throw new NotFoundException(String.format("Оценка Ассоциации кинокомпаний с идентификатором %d не найдена", mpaId));
        }

        return mpa.get();
    }
}
