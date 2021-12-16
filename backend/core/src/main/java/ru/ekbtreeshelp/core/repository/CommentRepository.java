package ru.ekbtreeshelp.core.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ekbtreeshelp.core.entity.Comment;

import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("FROM Comment WHERE tree.id = :treeId")
    List<Comment> findAllByTreeId(Long treeId);

    List<Comment> findAllByAuthorId(Long authorId, Pageable page);

    @Query("DELETE FROM Comment WHERE author.id = :authorId")
    List<Comment> deleteAllByAuthorId(Long authorId);
}
