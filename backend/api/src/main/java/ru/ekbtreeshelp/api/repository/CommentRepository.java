package ru.ekbtreeshelp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ekbtreeshelp.api.entity.Comment;

import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("FROM Comment WHERE tree.id = :treeId")
    List<Comment> findAllByTreeId(Long treeId);

}
