package ru.ekbtreeshelp.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ekbtreeshelp.api.converter.SpeciesTreeConverter;
import ru.ekbtreeshelp.api.dto.SpeciesTreeDto;
import ru.ekbtreeshelp.api.service.SpeciesTreeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/species", produces = MediaType.APPLICATION_JSON_VALUE)
public class SpeciesTreeController {

    private final SpeciesTreeService speciesTreeService;
    private final SpeciesTreeConverter speciesTreeConverter;

    @Operation(summary = "Сохраняет новую породу")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority(@Roles.SUPERUSER, @Roles.MODERATOR)")
    public Long save (@RequestBody SpeciesTreeDto speciesTreeDto) {
        return speciesTreeService.save(speciesTreeConverter.fromDto(speciesTreeDto));
    }

    @Operation(summary = "Предоставляет список пород деревьев")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-all")
    @PreAuthorize("permitAll()")
    public List<SpeciesTreeDto> getAll() {
        return speciesTreeConverter.toDto(speciesTreeService.getAll());
    }

    @Operation(summary = "Удаляет породу")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority(@Roles.SUPERUSER, @Roles.MODERATOR)")
    public void delete(@PathVariable Long id) {
        speciesTreeService.delete(id);
    }
}