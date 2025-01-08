package ru.yandex.practicum.filmorate.model;

// region imports

import lombok.Data;

// endregion

/**
 * Рейтинг Ассоциации кинокомпаний.
 */
@Data
public class MotionPictureAssociation {
    /**
     * Идентификатор оценки.
     */
    private Long id;

    /**
     * Название оценки.
     */
    private String name;

    /**
     * Описание оценки.
     */
    private String description;
}
