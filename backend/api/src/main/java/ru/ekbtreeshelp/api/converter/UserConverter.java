package ru.ekbtreeshelp.api.converter;

import org.mapstruct.Mapper;
import ru.ekbtreeshelp.api.dto.UserDto;
import ru.ekbtreeshelp.core.entity.User;

@Mapper(componentModel = "spring")
public interface UserConverter {
    UserDto toDto(User user);
}
