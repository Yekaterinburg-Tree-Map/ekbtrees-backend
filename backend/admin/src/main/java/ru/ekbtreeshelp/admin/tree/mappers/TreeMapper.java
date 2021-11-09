package ru.ekbtreeshelp.admin.tree.mappers;

import org.mapstruct.Mapper;
import ru.ekbtreeshelp.admin.tree.dtos.CreateNewTreeDto;
import ru.ekbtreeshelp.admin.tree.dtos.TreeResponseDto;
import ru.ekbtreeshelp.admin.tree.dtos.UpdateTreeDto;
import ru.ekbtreeshelp.core.entity.Tree;

@Mapper(componentModel = "spring")
public interface TreeMapper {
    Tree fromRequestDto(CreateNewTreeDto createNewTreeDto);
    Tree fromRequestDto(UpdateTreeDto updateTreeDto);
    TreeResponseDto toResponseDto(Tree tree);
}
