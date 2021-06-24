package ru.ekbtreeshelp.api.converter;

import org.mapstruct.Mapper;
import ru.ekbtreeshelp.api.dto.TreeMapInfoDto;
import ru.ekbtreeshelp.api.entity.Tree;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        PointConverter.class,
        SpeciesTreeConverter.class
})
public interface TreeMapInfoConverter {

    List<TreeMapInfoDto> toDto(List<Tree> treesMapInfo);
}
