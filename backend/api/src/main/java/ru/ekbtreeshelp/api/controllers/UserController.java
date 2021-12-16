package ru.ekbtreeshelp.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ekbtreeshelp.api.converter.UserConverter;
import ru.ekbtreeshelp.api.dto.UpdateUserDto;
import ru.ekbtreeshelp.api.dto.UpdateUserPasswordDto;
import ru.ekbtreeshelp.api.dto.UserDto;
import ru.ekbtreeshelp.api.service.SecurityService;
import ru.ekbtreeshelp.api.service.UserService;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;
    private final SecurityService securityService;

    @GetMapping("/{id}")
    @Operation(summary = "Предоставляет информацию о пользователе по идентификатору")
    public UserDto getById(@PathVariable Long id) {
        return userConverter.toDto(userService.getById(id));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Предоставляет информацию о текущем пользователе")
    public UserDto getSelf() {
        Long currentUserId = securityService.getCurrentUserId();
        return userConverter.toDto(userService.getById(currentUserId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(@Roles.SUPERUSER, @Roles.MODERATOR) or " +
            "hasPermission(#id, @Domains.USER, @Permissions.EDIT)")
    @Operation(summary = "Редактирует пользователя")
    public void updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserDto updateUserDto) {
        userService.update(id, updateUserDto);
    }

    @PutMapping("/updatePassword")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Редактирует пароль текущего пользователя")
    public void updatePassword(@RequestBody @Valid UpdateUserPasswordDto updateUserPasswordDto) {
        userService.updatePassword(securityService.getCurrentUser(), updateUserPasswordDto.getNewPassword());
    }
}
