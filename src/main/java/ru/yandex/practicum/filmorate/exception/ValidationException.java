package ru.yandex.practicum.filmorate.exception;

/**
 * Исключение, выбрасываемое, в случае, если не была пройдена валидация.
 */
public class ValidationException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param message сообщение об ошибке.
     */
    public ValidationException(String message) {
        super(message);
    }
}