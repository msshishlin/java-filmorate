package ru.yandex.practicum.filmorate.validation.film;

// region imports

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.validation.film.constraint.DurationConstraint;

import java.time.Duration;

// endregion

public class DurationValidator implements ConstraintValidator<DurationConstraint, Duration> {
    @Override
    public boolean isValid(Duration duration, ConstraintValidatorContext constraintValidatorContext) {
        return duration != null && duration.isPositive();
    }
}
