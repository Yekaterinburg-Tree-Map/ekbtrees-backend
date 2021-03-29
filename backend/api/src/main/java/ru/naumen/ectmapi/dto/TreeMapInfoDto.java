package ru.naumen.ectmapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Транспортная сущность информации о дереве, необходимой для отображения ее на карте")
@Data
public class TreeMapInfoDto {

    @Schema(description = "Идентификатор дерева")
    private Long id;

    @Schema(description = "Широта")
    private Double latitude;

    @Schema(description = "Долгота")
    private Double longitude;

    @Schema(description = "Диаметр кроны")
    private Double diameterOfCrown;

    @Schema(description = "Порода дерева")
    private String type;
}
