package ru.yandex.practicum.filmorate.exception;

/**
 * Исключение, выбрасываемое, в случае, если у сущности отсутствует обязательный идентификатор.
 */
public class MissedEntityIdException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param message сообщение об ошибке.
     */
    public MissedEntityIdException(String message) {
        super(message);
    }
}
