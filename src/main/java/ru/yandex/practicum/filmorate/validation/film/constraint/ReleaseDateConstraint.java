package ru.yandex.practicum.filmorate.validation.film.constraint;

// region imports

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.validation.film.ReleaseDateValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// endregion

@Constraint(validatedBy = ReleaseDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReleaseDateConstraint {
    String message() default "Дата релиза не может быть раньше 28 декабря 1895 года";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
