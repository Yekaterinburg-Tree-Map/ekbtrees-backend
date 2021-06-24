package ru.ekbtreeshelp.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Schema(description = "Результат проверки здоровья приложения")
@AllArgsConstructor
@RequiredArgsConstructor
public class HealthCheckDto {

    public enum Status {
        HEALTHY,
        UNHEALTHY
    }

    @Schema(description = "Информация о статусе приложения")
    private Status status;

}

