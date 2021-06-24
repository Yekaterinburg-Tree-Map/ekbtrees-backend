package ru.ekbtreeshelp.api.converter;

import org.mapstruct.Mapper;
import ru.ekbtreeshelp.api.dto.SpeciesTreeDto;
import ru.ekbtreeshelp.api.entity.SpeciesTree;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpeciesTreeConverter {

    List<SpeciesTreeDto> toDto(List<SpeciesTree> speciesTrees);

    SpeciesTree fromDto(SpeciesTreeDto speciesTreeDto);

    SpeciesTreeDto toDto(SpeciesTree speciesTree);
}
