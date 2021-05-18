package ru.naumen.ectmapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.naumen.ectmapi.dto.HealthCheckDto;

@RestController
@RequestMapping("/api/healthCheck")
public class HealthCheckController {

    @Operation(description = "Получение информации о статусе приложения")
    @GetMapping
    HealthCheckDto check() {
        return new HealthCheckDto(HealthCheckDto.Status.HEALTHY);
    }

}
