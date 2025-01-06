package ru.yandex.practicum.filmorate.validation.film;

// region imports

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.validation.film.constraint.ReleaseDateConstraint;

import java.time.LocalDate;

// endregion

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDateConstraint, LocalDate> {
    @Override
    public boolean isValid(LocalDate filmReleaseDate, ConstraintValidatorContext constraintValidatorContext) {
        return filmReleaseDate != null && !filmReleaseDate.isBefore(LocalDate.of(1895, 12, 28));
    }
}
