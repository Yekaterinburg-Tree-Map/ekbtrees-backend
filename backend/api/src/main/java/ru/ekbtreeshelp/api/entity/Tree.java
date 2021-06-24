package ru.ekbtreeshelp.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trees")
public class Tree extends BaseEntity {

    /**
     * Местоположение дерева в географической системе координат
     */
    @Column(name = "geo_point")
    private Point geoPoint;

    /**
     * Порода дерева
     */
    @ManyToOne
    @JoinColumn(name = "species_id")
    private SpeciesTree species;

    /**
     * Высота дерева в метрах
     */
    @Column(name = "tree_height")
    private Double treeHeight;

    /**
     * Число стволов
     */
    @Column(name = "number_of_tree_trunks")
    private Integer numberOfTreeTrunks;

    /**
     * Обхват (самого толстого) ствола в сантиметрах
     */
    @Column(name = "trunk_girth")
    private Double trunkGirth;

    /**
     * Диаметр кроны в метрах
     */
    @Column(name = "diameter_of_crown")
    private Double diameterOfCrown;

    /**
     * Высота первой ветви от земли в метрах
     */
    @Column(name = "height_of_the_first_branch")
    private Double heightOfTheFirstBranch;

    /**
     * Визуальная оценка состояния (по шкале 1 до 5)
     */
    @Column(name = "condition_assessment")
    private Integer conditionAssessment;

    /**
     * Возраст в годах
     */
    private Integer age;

    /**
     * Тип посадки дерева
     */
    @Column(name = "tree_planting_type")
    private String treePlantingType;

    /**
     * Автор дерева
     */
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    /**
     * Статус дерева
     */
    private String status;
}
