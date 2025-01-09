package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.MotionPictureAssociationDto;
import ru.yandex.practicum.filmorate.mapper.MotionPictureAssociationMapper;
import ru.yandex.practicum.filmorate.service.MotionPictureAssociationService;

import java.util.Collection;

/**
 * Контроллер, обрабатывающий запросы оценок Ассоциации кинокомпаний.
 */
@RequestMapping("/mpa")
@RestController
public class MotionPictureAssociationController {
    /**
     * Сервис для работы с оценками Ассоциации кинокомпаний.
     */
    @Autowired
    private MotionPictureAssociationService mpaService;

    /**
     * Получить список всех оценок Ассоциации кинокомпаний.
     *
     * @return список всех оценок Ассоциации кинокомпаний.
     */
    @GetMapping
    public Collection<MotionPictureAssociationDto> getAll() {
        return MotionPictureAssociationMapper.mapToMpaDtoCollection(this.mpaService.getAll());
    }

    /**
     * Получить оценку Ассоциации кинокомпаний по её идентификатору.
     *
     * @param mpaId идентификатор оценки Ассоциации кинокомпаний.
     * @return оценка Ассоциации кинокомпаний.
     */
    @GetMapping("/{mpaId}")
    public MotionPictureAssociationDto getMpaById(@PathVariable long mpaId) {
        return MotionPictureAssociationMapper.mapToMpaDto(this.mpaService.getMpaById(mpaId));
    }
}
