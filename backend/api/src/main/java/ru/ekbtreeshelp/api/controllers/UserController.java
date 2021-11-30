package ru.ekbtreeshelp.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ekbtreeshelp.api.converter.UserConverter;
import ru.ekbtreeshelp.api.dto.UserDto;
import ru.ekbtreeshelp.api.service.SecurityService;
import ru.ekbtreeshelp.api.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
//
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

    @GetMapping("/listUsersWithoutPaging")
    public List<UserDto> listUsersWithoutPaging(){
        return userService.listUsersWithoutPaging();
    }

    @GetMapping("/listUsers")
    public List<UserDto> listUsers(@RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        return userService.listAllUsers(pageNumber, pageSize);
    }

    @PutMapping
    public UserDto update(@RequestBody UserDto userData){
        UserDto user = userService.update(userData);
        return user;
    }

    @GetMapping("/block/{id}")
    public void blockUser(@RequestBody UserDto userData){
        userService.blockUser(userData.getId());
    }
}
