package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MotionPictureAssociation;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Конвертер строки таблицы БД в объект рейтинга Ассоциации кинокомпаний.
 */
@Component
public final class MotionPictureAssociationRowMapper implements RowMapper<MotionPictureAssociation> {
    @Override
    public MotionPictureAssociation mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        MotionPictureAssociation mpa = new MotionPictureAssociation();

        mpa.setId(resultSet.getLong("id"));
        mpa.setName(resultSet.getString("name"));
        mpa.setDescription(resultSet.getString("description"));

        return mpa;
    }
}
