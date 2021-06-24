package ru.ekbtreeshelp.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ekbtreeshelp.api.dto.TreeDto;
import ru.ekbtreeshelp.api.entity.Tree;

@Mapper(
        componentModel = "spring",
        uses = {
                PointConverter.class,
                SpeciesTreeConverter.class
        }
)
public interface TreeConverter {

    @Mapping(source = "geoPoint", target = "geographicalPoint")
    TreeDto toDto(Tree tree);

    @Mapping(source = "geographicalPoint", target = "geoPoint")
    Tree fromDto(TreeDto treeDto);
}
