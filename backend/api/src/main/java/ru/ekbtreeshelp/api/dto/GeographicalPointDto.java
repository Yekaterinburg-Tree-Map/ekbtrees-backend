package ru.ekbtreeshelp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Транспортная сущность точки в географической системе координат")
public class GeographicalPointDto {

    @Schema(description = "Широта")
    @NotNull
    @Min(-90)
    @Max(90)
    private Double latitude;

    @Schema(description = "Долгота")
    @NotNull
    @Min(-180)
    @Max(180)
    private Double longitude;

}
