package ru.yandex.practicum.filmorate.mapper;

import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.MotionPictureAssociationDto;
import ru.yandex.practicum.filmorate.model.MotionPictureAssociation;

import java.util.Collection;

@NoArgsConstructor
public final class MotionPictureAssociationMapper {
    public static MotionPictureAssociation mapToMpa(MotionPictureAssociationDto dto) {
        MotionPictureAssociation mpa = new MotionPictureAssociation();

        mpa.setId(dto.getId());
        mpa.setName(dto.getName());
        mpa.setDescription(dto.getDescription());

        return mpa;
    }

    public static MotionPictureAssociationDto mapToMpaDto(MotionPictureAssociation mpa) {
        MotionPictureAssociationDto dto = new MotionPictureAssociationDto();

        dto.setId(mpa.getId());
        dto.setName(mpa.getName());
        dto.setDescription(mpa.getDescription());

        return dto;
    }

    public static Collection<MotionPictureAssociationDto> mapToMpaDtoCollection(Collection<MotionPictureAssociation> mpaCollection) {
        return mpaCollection.stream().map(MotionPictureAssociationMapper::mapToMpaDto).toList();
    }
}
