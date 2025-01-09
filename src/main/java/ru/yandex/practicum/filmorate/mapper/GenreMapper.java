package ru.yandex.practicum.filmorate.mapper;

import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@NoArgsConstructor
public final class GenreMapper {
    public static Genre mapToGenre(GenreDto dto) {
        Genre genre = new Genre();

        genre.setId(dto.getId());
        genre.setName(dto.getName());

        return genre;
    }

    public static Collection<Genre> mapToGenreCollection(Collection<GenreDto> dtoCollection) {
        return dtoCollection.stream().map(GenreMapper::mapToGenre).toList();
    }

    public static GenreDto mapToGenreDto(Genre genre) {
        GenreDto dto = new GenreDto();

        dto.setId(genre.getId());
        dto.setName(genre.getName());

        return dto;
    }

    public static Collection<GenreDto> mapToGenreDtoCollection(Collection<Genre> genreCollection) {
        return genreCollection.stream().map(GenreMapper::mapToGenreDto).toList();
    }
}
