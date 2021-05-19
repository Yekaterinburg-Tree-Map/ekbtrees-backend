package ru.naumen.ectmapi.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.naumen.ectmapi.entity.SpeciesTree;

import java.util.List;

@Mapper
public interface SpeciesTreeRepository {

    void create(@Param("speciesTree") SpeciesTree speciesTree);

    List <SpeciesTree> findAll();

    void delete(@Param("id") Long id);

    boolean isExists(@Param("id") Long id);
}
