package ru.ekbtreeshelp.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ekbtreeshelp.core.entity.FileEntity;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findFirstByHash(String hash);

    List<FileEntity> findAllByTreeId(Long treeId);

    @Modifying
    @Query("UPDATE FileEntity AS file SET file.tree.id = :treeId WHERE file.id = :id")
    void updateTreeId(Long id, Long treeId);

    long countByHash(String hash);

}
