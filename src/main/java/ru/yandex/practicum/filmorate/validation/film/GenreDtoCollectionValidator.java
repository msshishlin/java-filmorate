package ru.yandex.practicum.filmorate.validation.film;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.abstractions.GenreStorage;
import ru.yandex.practicum.filmorate.validation.film.constraint.GenreDtoCollectionConstraint;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GenreDtoCollectionValidator implements ConstraintValidator<GenreDtoCollectionConstraint, Collection<GenreDto>> {
    @Autowired
    @Qualifier(GenreDbStorage.CLASS_NAME)
    private GenreStorage genreStorage;

    @Override
    public boolean isValid(Collection<GenreDto> genreDtoCollection, ConstraintValidatorContext constraintValidatorContext) {
        Set<String> errors = new HashSet<>();

        genreDtoCollection.forEach(dto -> {
            Optional<Genre> genre = this.genreStorage.getGenreById(dto.getId());
            if (genre.isEmpty()) {
                errors.add(String.format("Жанр с идентификатором %d не найден", dto.getId()));
            }
        });

        if (!errors.isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(String.join(", ", errors)).addConstraintViolation();

            return false;
        }

        return true;
    }
}
