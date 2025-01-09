package ru.yandex.practicum.filmorate.validation.film;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.dto.MotionPictureAssociationDto;
import ru.yandex.practicum.filmorate.storage.MotionPictureAssociationDbStorage;
import ru.yandex.practicum.filmorate.storage.abstractions.MotionPictureAssociationStorage;
import ru.yandex.practicum.filmorate.validation.film.constraint.MotionPictureAssociationDtoConstraint;

public class MotionPictureAssociationDtoValidator implements ConstraintValidator<MotionPictureAssociationDtoConstraint, MotionPictureAssociationDto> {
    @Autowired
    @Qualifier(MotionPictureAssociationDbStorage.CLASS_NAME)
    private MotionPictureAssociationStorage mpaStorage;

    @Override
    public boolean isValid(MotionPictureAssociationDto mpaDto, ConstraintValidatorContext constraintValidatorContext) {
        if (this.mpaStorage.getMpaById(mpaDto.getId()).isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(String.format("Оценка Ассоциации кинокомпаний с идентификатором %d не найдена", mpaDto.getId())).addConstraintViolation();

            return false;
        }

        return true;
    }
}
