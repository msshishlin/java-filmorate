package ru.yandex.practicum.filmorate.dto;

import lombok.Data;

/**
 * Трансферный объект для сущности "Оценка Ассоциации кинокомпаний".
 */
@Data
public final class MotionPictureAssociationDto {
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
