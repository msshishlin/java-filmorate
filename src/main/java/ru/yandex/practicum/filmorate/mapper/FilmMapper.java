package ru.yandex.practicum.filmorate.mapper;

import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Set;

@NoArgsConstructor
public final class FilmMapper {
    public static Film mapToFilm(FilmDto dto) {
        Film film = new Film();

        film.setId(dto.getId());
        film.setName(dto.getName());
        film.setDescription(dto.getDescription());
        film.setReleaseDate(dto.getReleaseDate());
        film.setDuration(dto.getDuration());
        film.setMpa(MotionPictureAssociationMapper.mapToMpa(dto.getMpa()));
        film.setGenres(Set.copyOf(GenreMapper.mapToGenreCollection(dto.getGenres())));

        return film;
    }

    public static FilmDto mapToFilmDto(Film film) {
        FilmDto dto = new FilmDto();

        dto.setId(film.getId());
        dto.setName(film.getName());
        dto.setDescription(film.getDescription());
        dto.setReleaseDate(film.getReleaseDate());
        dto.setDuration(film.getDuration());
        dto.setMpa(MotionPictureAssociationMapper.mapToMpaDto(film.getMpa()));
        dto.setGenres(Set.copyOf(GenreMapper.mapToGenreDtoCollection(film.getGenres())));

        return dto;
    }

    public static Collection<FilmDto> mapToFilmDtoCollection(Collection<Film> filmCollection) {
        return filmCollection.stream().map(FilmMapper::mapToFilmDto).toList();
    }
}
