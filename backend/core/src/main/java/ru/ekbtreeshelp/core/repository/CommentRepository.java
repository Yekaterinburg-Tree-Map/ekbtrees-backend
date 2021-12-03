package ru.ekbtreeshelp.core.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ekbtreeshelp.core.entity.Comment;

import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByTreeId(Long treeId, PageRequest page);

    List<Comment> findAllByAuthorId(Long authorId, PageRequest page);

    @Query("DELETE FROM Comment WHERE author.id = :authorId")
    List<Comment> deleteAllByAuthorId(Long authorId);
}
