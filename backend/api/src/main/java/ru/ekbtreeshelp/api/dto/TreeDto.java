package ru.ekbtreeshelp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Транспортная сущность дерева")
public class TreeDto {

    @Schema(description = "Идентификатор дерева")
    private Long id;

    @Schema(description = "Местоположение дерева в географической системе координат")
    @NotNull
    private GeographicalPointDto geographicalPoint;

    @Schema(description = "Порода дерева")
    @NotNull
    private SpeciesTreeDto species;

    @Schema(description = "Высота дерева в метрах")
    private Double treeHeight;

    @Schema(description = "Число стволов (целое)")
    private Integer numberOfTreeTrunks;

    @Schema(description = "Обхват (самого толстого) ствола в сантиметрах")
    private Double trunkGirth;

    @Schema(description = "Диаметр кроны в метрах")
    private Double diameterOfCrown;

    @Schema(description = "Высота первой ветви от земли в метрах")
    private Double heightOfTheFirstBranch;

    @Schema(description = "Визуальная оценка состояния (по шкале 1 до 5)")
    private Integer conditionAssessment;

    @Schema(description = "Возраст в годах")
    private Integer age;

    @Schema(description = "Тип посадки дерева")
    private String treePlantingType;

    @Schema(description = "Дата и время добавления записи")
    private Long created;

    @Schema(description = "Дата и время последнего редактирования информации о дереве")
    private Long updated;

    @Schema(description = "Ссылка на автора")
    private Long authorId;

    @Schema(description = "Статус дерева")
    private String status;

    @Schema(description = "Список идентификаторов файлов, связанных с деревом")
    private Collection<Long> fileIds;

    @Schema(description = "Флаг возможности редактирования дерева текущим пользователем")
    private boolean editable;

    @Schema(description = "Флаг возможности удаления дерева текущим пользователем")
    private boolean deletable;

}
