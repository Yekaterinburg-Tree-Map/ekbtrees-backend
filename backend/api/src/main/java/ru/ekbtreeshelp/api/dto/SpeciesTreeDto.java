package ru.ekbtreeshelp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Транспортная сущность породы дерева")
public class SpeciesTreeDto {

    @Schema(description = "Идентификатор породы")
    private Long id;

    @Schema(description = "Название породы")
    private String title;

}
