package ru.yandex.practicum.filmorate.exception;

/**
 * Исключение, выбрасываемое в случае, если объект не найден.
 */
public class NotFoundException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param message сообщение об ошибке.
     */
    public NotFoundException(String message) {
        super(message);
    }
}