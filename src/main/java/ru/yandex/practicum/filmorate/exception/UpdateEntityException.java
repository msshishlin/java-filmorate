package ru.yandex.practicum.filmorate.exception;

// region imports

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// endregion

/**
 * Исключение, выбрасываемое в случае, если обновление сущности невозможно.
 */
@Getter
@RequiredArgsConstructor
public class UpdateEntityException extends RuntimeException {
    /**
     * Причина, по которой обновление невозможно.
     */
    private final String reason;
}
