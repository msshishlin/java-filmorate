package ru.yandex.practicum.filmorate.validation.film.constraint;

// region imports

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.validation.film.DurationValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// endregion

@Constraint(validatedBy = DurationValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DurationConstraint {
    String message() default "Продолжительность фильма должна быть положительным числом";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
