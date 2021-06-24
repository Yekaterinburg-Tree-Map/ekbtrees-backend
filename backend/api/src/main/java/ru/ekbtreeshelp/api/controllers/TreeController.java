package ru.ekbtreeshelp.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ekbtreeshelp.api.converter.TreeConverter;
import ru.ekbtreeshelp.api.dto.TreeDto;
import ru.ekbtreeshelp.api.service.SecurityService;
import ru.ekbtreeshelp.api.service.TreeService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/tree", produces = MediaType.APPLICATION_JSON_VALUE)
public class TreeController {

    private final TreeService treeService;
    private final TreeConverter treeConverter;
    private final SecurityService securityService;

    @Operation(summary = "Сохраняет дерево")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save")
    @PreAuthorize("isAuthenticated()")
    public Long save(@RequestBody TreeDto treeDto) {
        return treeService.save(treeDto);
    }

    @Operation(summary = "Предоставляет дерево по id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get/{id}")
    @PreAuthorize("permitAll()")
    public TreeDto get(@PathVariable Long id) {
        return treeConverter.toDto(treeService.get(id));
    }

    @Operation(summary = "Предоставляет деревья текущего пользователя")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get")
    @PreAuthorize("isAuthenticated()")
    public List<TreeDto> get() {
        Long authorId = securityService.getCurrentUserId();
        return treeService.getAllByAuthorId(authorId).stream().map(treeConverter::toDto).collect(Collectors.toList());
    }

    @Operation(summary = "Удаляет дерево по id")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority(@Roles.SUPERUSER, @Roles.MODERATOR) " +
            "or hasPermission(#id, @Domains.TREE, @Permissions.DELETE)")
    public void delete(@PathVariable Long id) {
        treeService.delete(id);
    }

    @Operation(
            summary = "Загружает файл в хранилище и прикрепляет его к дереву",
            responses = {
                    @ApiResponse(
                            description = "id загружаемого файла"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Формат multipart/form-data, один файл с ключом 'file'",
                    required = true,
                    content = {
                            @Content(
                                    mediaType = "multipart/form-data",
                                    schema = @Schema(
                                            type = "object",
                                            requiredProperties = "file"
                                    )
                            )
                    }
            )
    )
    @PostMapping("/attachFile/{treeId}")
    @PreAuthorize("isAuthenticated()")
    public Long attachFile(@PathVariable Long treeId, @RequestParam("file") MultipartFile file) {
        return treeService.attachFile(treeId, file);
    }

}
