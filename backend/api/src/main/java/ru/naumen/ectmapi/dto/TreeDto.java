package ru.naumen.ectmapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.time.Instant;
import java.util.UUID;

@Schema
@Data
@AllArgsConstructor
public class TreeDto {

    @Schema(description = "Идентификатор дерева")
    private Long id;

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

    @Schema(description = "Порода дерева")
    private String type;

    @Schema(description = "Высота дерева в метрах")
    private Double treeHeight;

    @Schema(description = "Число стволов (целое)")
    private int numberOfTreeTrunks;

    @Schema(description = "Обхват (самого толстого) ствола в сантиметрах")
    private Double trunkGirth;

    @Schema(description = "Диаметр кроны в метрах")
    private Double diameterOfCrown;

    @Schema(description = "Высота первой ветви от земли в метрах")
    private Double heightOfTheFirstBranch;

    @Schema(description = "Визуальная оценка состояния (по шкале 1 до 5)")
    private int conditionAssessment;

    @Schema(description = "Возраст в годах")
    private int age;

    @Schema(description = "Тип посадки дерева")
    private String treePlantingType;

    @Schema(description = "Дата и время добавления записи")
    private Instant created;

    @Schema(description = "Дата и время последнего редактирования информации о дереве")
    private Instant updated;

    @Schema(description = "Ссылка на автора")
    private Long authorId;

    @Schema(description = "Статус дерева")
    private String status;

    public TreeDto() {

    }
}
