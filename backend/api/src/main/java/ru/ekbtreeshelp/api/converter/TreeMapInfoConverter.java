package ru.ekbtreeshelp.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ekbtreeshelp.api.dto.TreeMapInfoDto;
import ru.ekbtreeshelp.core.entity.Tree;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        PointConverter.class,
        SpeciesTreeConverter.class
})
public interface TreeMapInfoConverter {

    @Mapping(source = "geoPoint", target = "geographicalPoint")
    TreeMapInfoDto toDto(Tree tree);

    List<TreeMapInfoDto> toDto(List<Tree> treesMapInfo);
}
