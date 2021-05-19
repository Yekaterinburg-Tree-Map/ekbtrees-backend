package ru.naumen.ectmapi.converter;

import org.mapstruct.Mapper;
import ru.naumen.ectmapi.dto.SpeciesTreeDto;
import ru.naumen.ectmapi.entity.SpeciesTree;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpeciesTreeConverter {

    List<SpeciesTreeDto> toDto(List<SpeciesTree> speciesTrees);

    SpeciesTree fromDto(SpeciesTreeDto speciesTreeDto);

    SpeciesTreeDto toDto(SpeciesTree speciesTree);
}
