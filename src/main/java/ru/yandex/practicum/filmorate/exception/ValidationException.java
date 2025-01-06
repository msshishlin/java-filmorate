package ru.yandex.practicum.filmorate.exception;

// region imports

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.ObjectError;

import java.util.Collection;

// endregion

/**
 * Исключение, выбрасываемое, в случае, если не была пройдена валидация.
 */
@Getter
@RequiredArgsConstructor
public class ValidationException extends RuntimeException {
    /**
     * Коллекция ошибок валидации.
     */
    private final Collection<ObjectError> errors;
}