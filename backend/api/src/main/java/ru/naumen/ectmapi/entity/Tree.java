package ru.naumen.ectmapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.postgis.Point;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
public class Tree extends Entity<Long> {

    /**
     * Местоположение дерева в географической системе координат
     */
    private Point geographicalPoint;

    /**
     * Порода дерева
     */
    private SpeciesTree species;

    /**
     * Высота дерева в метрах
     */
    private Double treeHeight;

    /**
     * Число стволов
     */
    private Integer numberTreeTrunks;

    /**
     * Обхват (самого толстого) ствола в сантиметрах
     */
    private Double trunkGirth;

    /**
     * Диаметр кроны в метрах
     */
    private Double diameterCrown;

    /**
     * Высота первой ветви от земли в метрах
     */
    private Double heightFirstBranch;

    /**
     * Визуальная оценка состояния (по шкале 1 до 5)
     */
    private Integer conditionAssessment;

    /**
     * Возраст в годах
     */
    private Integer age;

    /**
     * Тип посадки дерева
     */
    private String treePlantingType;

    /**
     * Дата и время добавления записи
     */
    private Instant created;

    /**
     * Дата и время последнего редактирования информации о дереве
     */
    private Instant updated;

    /**
     * Ссылка на автора
     */
    private Long authorId;

    /**
     * Статус дерева
     */
    private String status;
}
