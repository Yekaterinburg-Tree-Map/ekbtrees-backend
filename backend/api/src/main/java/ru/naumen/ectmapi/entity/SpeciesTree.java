package ru.naumen.ectmapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SpeciesTree extends Entity<Long>{

    /**
     * Название породы дерева
     */
    private String title;
}
