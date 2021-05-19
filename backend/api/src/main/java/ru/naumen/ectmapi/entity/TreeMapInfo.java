package ru.naumen.ectmapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.postgis.Point;

@EqualsAndHashCode(callSuper = true)
@Data
public class TreeMapInfo extends Entity<Long> {

    /**
     * Местоположение дерева (точки) в географической системе координат
     */
    private Point geographicalPoint;

    /**
     * Диаметр кроны
     */
    private Double diameterCrown;

    /**
     * Порода дерева
     */
    private SpeciesTree species;
}
