package ru.ekbtreeshelp.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ekbtreeshelp.api.converter.CommentConverter;
import ru.ekbtreeshelp.api.dto.CommentDto;
import ru.ekbtreeshelp.api.service.CommentService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/comment", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityScheme(name = "jwt", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
@SecurityRequirement(name = "jwt")
public class CommentController {

    private final CommentService commentService;
    private final CommentConverter commentConverter;

    @Operation(summary = "Сохраняет комментарий")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save")
    public Long save(@RequestBody CommentDto commentDto) {
        return commentService.save(commentDto);
    }

    @Operation(summary = "Предоставляет комментарии по id дерева")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-all/{treeId}")
    public List<CommentDto> getAllByTreeId(@PathVariable Long treeId, @RequestBody Integer page, @RequestBody Integer size) {
        return commentConverter.toDto(commentService.getAllByTreeId(treeId, page, size));
    }

    @Operation(summary = "Удаляет комментарий по id")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority(@Roles.MODERATOR, @Roles.SUPERUSER) " +
            "or hasPermission(#id, @Domains.COMMENT, @Permissions.DELETE)")
    public void delete(@PathVariable Long id){ commentService.delete(id);}

    @Operation(summary = "Предоставляет комментарии по id пользователя")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-all/{authorId}")
    public List<CommentDto> getAllByAuthorId(@PathVariable Long authorId, @RequestBody Integer page, @RequestBody Integer size) {
        return commentConverter.toDto(commentService.getAllByAuthorId(authorId, page, size));
    }

    @Operation(summary = "Удаляет все комментарии по id пользователя")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{authorId}")
    @PreAuthorize("hasAnyAuthority(@Roles.MODERATOR, @Roles.SUPERUSER) " +
            "or hasPermission(#id, @Domains.COMMENT, @Permissions.DELETE)")
    public void deleteAllComment(@PathVariable Long authorId) { commentService.deleteAllCommentByAuthor(authorId); }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/edit")
    public Long edit(@RequestBody CommentDto commentDto)
    {
        return commentService.save(commentDto);
    }
}
