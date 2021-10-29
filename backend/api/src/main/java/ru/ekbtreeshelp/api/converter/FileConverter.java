package ru.ekbtreeshelp.api.converter;


import org.mapstruct.Mapper;
import ru.ekbtreeshelp.api.dto.FileDto;
import ru.ekbtreeshelp.core.entity.FileEntity;

@Mapper(componentModel = "spring")
public interface FileConverter {

    FileDto toDto(FileEntity fileEntity);

    FileEntity fromDto(FileDto fileDto);

}
