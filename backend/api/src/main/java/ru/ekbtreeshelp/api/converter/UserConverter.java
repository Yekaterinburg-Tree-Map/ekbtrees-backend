package ru.ekbtreeshelp.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.ekbtreeshelp.api.dto.UpdateUserDto;
import ru.ekbtreeshelp.api.dto.UserDto;
import ru.ekbtreeshelp.core.entity.User;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserConverter {
    UserDto toDto(User user);
    void updateUserFromDto(UpdateUserDto updateUserDto, @MappingTarget User user);
}
