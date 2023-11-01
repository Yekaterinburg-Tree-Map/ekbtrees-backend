package ru.ekbtreeshelp.api.tree.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Транспортная сущность информации о дереве, необходимой для отображения ее на карте")
public class TreeMapInfoDto {

    @Schema(description = "Идентификатор дерева")
    private Long id;

    @Schema(description = "Местоположение дерева в географической системе координат")
    private GeographicalPointDto geographicalPoint;

    @Schema(description = "Диаметр кроны")
    private Double diameterOfCrown;

    @Schema(description = "Порода дерева")
    private SpeciesTreeDto species;

    @Schema(description = "Признак наличия подтверждения дерева модератором")
    private boolean approvedByModerator;

    @Schema(description = "Статус дерева")
    private String status;
}
