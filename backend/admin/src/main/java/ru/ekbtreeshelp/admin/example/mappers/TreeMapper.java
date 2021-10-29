package ru.ekbtreeshelp.admin.example.mappers;

import org.mapstruct.Mapper;
import ru.ekbtreeshelp.admin.example.dtos.*;
import ru.ekbtreeshelp.core.entity.Tree;

@Mapper(componentModel = "spring")
public interface TreeMapper {
    Tree fromRequestDto(CreateNewTreeDto createNewTreeDto);
    Tree fromRequestDto(UpdateTreeDto createNewTreeDto);
    TreeResponseDto toResponseDto(Tree tree);
}
