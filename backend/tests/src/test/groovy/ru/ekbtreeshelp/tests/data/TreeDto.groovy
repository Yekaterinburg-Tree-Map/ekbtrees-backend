package ru.ekbtreeshelp.tests.data

import groovy.transform.Canonical

import java.time.Instant

@Canonical
class TreeDto {

    Long id;

    GeographicalPointDto geographicalPoint;

    SpeciesTreeDto species;

    Double treeHeight;

    Integer numberOfTreeTrunks;

    Double trunkGirth;

    Double diameterOfCrown;

    Double heightOfTheFirstBranch;

    Integer conditionAssessment;

    Integer age;

    String treePlantingType;

    Instant created;

    Instant updated;

    Long authorId;

    String status;

    Collection<Long> fileIds;

}
