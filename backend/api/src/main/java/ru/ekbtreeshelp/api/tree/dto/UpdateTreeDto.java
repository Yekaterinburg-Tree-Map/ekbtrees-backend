package ru.ekbtreeshelp.api.tree.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Транспортная сущность редактируемого дерева")
public class UpdateTreeDto {

    @Schema(description = "Местоположение дерева в географической системе координат")
    private GeographicalPointDto geographicalPoint;

    @Schema(description = "Идентификатор породы дерева")
    private Long speciesId;

    @Min(0)
    @Schema(description = "Высота дерева в метрах")
    private Double treeHeight;

    @Min(1)
    @Schema(description = "Число стволов (целое)")
    private Integer numberOfTreeTrunks;

    @Min(1)
    @Schema(description = "Обхват (самого толстого) ствола в сантиметрах")
    private Double trunkGirth;

    @Min(1)
    @Schema(description = "Диаметр кроны в метрах")
    private Double diameterOfCrown;

    @Min(0)
    @Schema(description = "Высота первой ветви от земли в метрах")
    private Double heightOfTheFirstBranch;

    @Min(1)
    @Max(5)
    @Schema(description = "Визуальная оценка состояния (по шкале 1 до 5)")
    private Integer conditionAssessment;

    @Min(0)
    @Schema(description = "Возраст в годах")
    private Integer age;

    @Schema(description = "Тип посадки дерева")
    private String treePlantingType;

    @Schema(description = "Статус дерева")
    private String status;

    @Schema(description = "Список идентификаторов файлов, связанных с деревом")
    private Collection<Long> fileIds;

    @Schema(description = "Обрезка")
    private String pruning;

    @Schema(description = "Прикорневые условия")
    private String rootCondition;

    @Schema(description = "Состояние стволов")
    private Collection<String> trunkStates;

    @Schema(description = "Состояние ветвей")
    private Collection<String> branchStates;

    @Schema(description = "Состояние коры")
    private Collection<String> corticalStates;
}
