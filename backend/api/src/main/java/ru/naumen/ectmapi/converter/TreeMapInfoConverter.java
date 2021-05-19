package ru.naumen.ectmapi.converter;

import org.mapstruct.Mapper;
import ru.naumen.ectmapi.dto.TreeMapInfoDto;
import ru.naumen.ectmapi.entity.TreeMapInfo;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        PointConverter.class,
        SpeciesTreeConverter.class
})
public interface TreeMapInfoConverter {

    List<TreeMapInfoDto> toDto(List<TreeMapInfo> treesMapInfo);
}
