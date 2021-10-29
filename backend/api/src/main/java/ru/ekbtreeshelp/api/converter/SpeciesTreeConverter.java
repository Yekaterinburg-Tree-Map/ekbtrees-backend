package ru.ekbtreeshelp.api.converter;

import org.mapstruct.Mapper;
import ru.ekbtreeshelp.api.dto.SpeciesTreeDto;
import ru.ekbtreeshelp.core.entity.SpeciesTree;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpeciesTreeConverter {

    List<SpeciesTreeDto> toDto(List<SpeciesTree> speciesTrees);

    SpeciesTree fromDto(SpeciesTreeDto speciesTreeDto);

    default SpeciesTree fromLong(Long speciesId) {
        SpeciesTree speciesTree = new SpeciesTree();
        speciesTree.setId(speciesId);
        return speciesTree;
    }

    SpeciesTreeDto toDto(SpeciesTree speciesTree);
}
