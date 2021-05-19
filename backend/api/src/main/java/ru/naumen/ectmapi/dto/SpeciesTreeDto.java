package ru.naumen.ectmapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;


@Schema(description = "Транспортная сущность породы дерева")
@Data
@AllArgsConstructor
public class SpeciesTreeDto {

    @Schema(description = "Идентификатор породы")
    private Long id;

    @Schema(description = "Название породы")
    private String title;
}
