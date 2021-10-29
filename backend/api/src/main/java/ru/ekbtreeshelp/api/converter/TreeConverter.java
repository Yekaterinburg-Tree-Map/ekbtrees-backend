package ru.ekbtreeshelp.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ekbtreeshelp.api.dto.CreateTreeDto;
import ru.ekbtreeshelp.api.dto.TreeDto;
import ru.ekbtreeshelp.core.entity.Tree;

@Mapper(
        componentModel = "spring",
        uses = {
                PointConverter.class,
                SpeciesTreeConverter.class
        }
)
public interface TreeConverter {

    @Mapping(source = "geoPoint", target = "geographicalPoint")
    @Mapping(source = "creationDate", target = "created")
    @Mapping(source = "lastModificationDate", target = "updated")
    TreeDto toDto(Tree tree);

    @Mapping(source = "geographicalPoint", target = "geoPoint")
    Tree fromDto(TreeDto treeDto);

    @Mapping(source = "geographicalPoint", target = "geoPoint")
    @Mapping(source = "speciesId", target = "species")
    Tree fromDto(CreateTreeDto createTreeDto);
}
