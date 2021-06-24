package ru.ekbtreeshelp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ekbtreeshelp.api.entity.SpeciesTree;

@Repository
@Transactional
public interface SpeciesTreeRepository extends JpaRepository<SpeciesTree, Long> {
    boolean existsByTitle(String title);
}
