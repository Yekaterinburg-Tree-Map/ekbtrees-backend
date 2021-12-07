package ru.ekbtreeshelp.api.converter;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ekbtreeshelp.api.dto.CreateTreeDto;
import ru.ekbtreeshelp.api.dto.UpdateTreeDto;
import ru.ekbtreeshelp.api.dto.TreeDto;
import ru.ekbtreeshelp.api.security.permissions.constants.Domains;
import ru.ekbtreeshelp.api.security.permissions.constants.Permissions;
import ru.ekbtreeshelp.api.security.permissions.evaluators.MainPermissionEvaluator;
import ru.ekbtreeshelp.api.service.SecurityService;
import ru.ekbtreeshelp.core.entity.Tree;

@Mapper(
        componentModel = "spring",
        uses = {
                PointConverter.class,
                SpeciesTreeConverter.class,
                DatesMapper.class
        },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class TreeConverter {
    @Autowired
    private MainPermissionEvaluator mainPermissionEvaluator;
    @Autowired
    private SecurityService securityService;

    @Mapping(source = "geoPoint", target = "geographicalPoint")
    @Mapping(source = "creationDate", target = "created")
    @Mapping(source = "lastModificationDate", target = "updated")
    public abstract TreeDto toDto(Tree tree);

    @AfterMapping
    protected TreeDto setRightsFlags(@MappingTarget TreeDto treeDto) {
        treeDto.setDeletable(mainPermissionEvaluator.hasPermission(
                securityService.getCurrentAuth(), treeDto.getId(), Domains.TREE, Permissions.DELETE));
        treeDto.setEditable(mainPermissionEvaluator.hasPermission(
                securityService.getCurrentAuth(), treeDto.getId(), Domains.TREE, Permissions.EDIT));
        return treeDto;
    }

    @Mapping(source = "geographicalPoint", target = "geoPoint")
    @Mapping(source = "speciesId", target = "species")
    public abstract Tree fromDto(CreateTreeDto createTreeDto);

    @Mapping(target = "geoPoint", source = "geographicalPoint")
    public abstract void updateTreeFromDto(UpdateTreeDto updateTreeDto, @MappingTarget Tree tree);
}
