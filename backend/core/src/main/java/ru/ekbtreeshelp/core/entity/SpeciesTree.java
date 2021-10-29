package ru.ekbtreeshelp.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "species_tree")
public class SpeciesTree extends BaseEntity {

    /**
     * Название породы дерева
     */
    @Column
    private String title;
}
