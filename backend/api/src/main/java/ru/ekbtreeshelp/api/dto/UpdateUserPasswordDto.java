package ru.ekbtreeshelp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Транспортная сущность информации о новом пароле пользователя")
public class UpdateUserPasswordDto {

    @Schema(description = "Новый пароль")
    private String newPassword;
}
