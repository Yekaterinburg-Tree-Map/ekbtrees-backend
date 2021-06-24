package ru.ekbtreeshelp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Кластер деревьев")
public class TreeClusterDto {

    @Schema(description = "Географическая точка, центр кластера")
    private GeographicalPointDto centre;

    @Schema(description = "Количество деревьев в кластере")
    private int count;

}
