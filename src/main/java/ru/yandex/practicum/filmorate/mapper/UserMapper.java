package ru.yandex.practicum.filmorate.mapper;

import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@NoArgsConstructor
public class UserMapper {
    public static User mapToUser(UserDto dto) {
        User user = new User();

        user.setId(dto.getId());
        user.setLogin(dto.getLogin());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setBirthday(dto.getBirthday());

        return user;
    }

    public static UserDto mapToUserDto(User user) {
        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setBirthday(user.getBirthday());

        return dto;
    }

    public static Collection<UserDto> mapToUserCollectionDto(Collection<User> userCollection) {
        return userCollection.stream().map(UserMapper::mapToUserDto).toList();
    }
}
