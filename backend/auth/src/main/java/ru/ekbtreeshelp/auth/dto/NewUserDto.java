package ru.ekbtreeshelp.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Schema(description = "Транспортная сущность нового пользователя")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class NewUserDto {

    @Schema(description = "Имя пользователя")
    private String firstName;

    @Schema(description = "Фамилия пользователя")
    private String lastName;

    @Schema(description = "Email пользователя", required = true)
    private String email;

    @Schema(description = "Пароль пользователя", required = true)
    private String password;
}
