package ru.ekbtreeshelp.admin.example.mappers;

import org.mapstruct.Mapper;
import ru.ekbtreeshelp.admin.example.dtos.CreateNewExampleDto;
import ru.ekbtreeshelp.admin.example.dtos.ExampleResponseDto;
import ru.ekbtreeshelp.admin.example.dtos.UpdateExampleDto;
import ru.ekbtreeshelp.core.entity.Example;

@Mapper(componentModel = "spring")
public interface ExampleMapper {
    Example fromRequestDto(CreateNewExampleDto createNewExampleDto);
    Example fromRequestDto(UpdateExampleDto createNewExampleDto);
    ExampleResponseDto toResponseDto(Example example);
}
