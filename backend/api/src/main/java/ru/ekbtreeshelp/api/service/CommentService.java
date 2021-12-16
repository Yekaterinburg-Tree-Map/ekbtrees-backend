package ru.ekbtreeshelp.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.ekbtreeshelp.api.converter.CommentConverter;
import ru.ekbtreeshelp.api.dto.CommentDto;
import ru.ekbtreeshelp.core.entity.Comment;
import ru.ekbtreeshelp.core.entity.Tree;
import ru.ekbtreeshelp.core.repository.CommentRepository;
import ru.ekbtreeshelp.core.repository.TreeRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;
    private final TreeRepository treeRepository;
    private final SecurityService securityService;

    @Transactional
    public Long save(CommentDto comment) {
        Optional<Tree> tree = treeRepository.findById(comment.getTreeId());

        if(tree.isEmpty()) {
            throw new IllegalStateException("Tree does not exist");
        }

        Comment commentEntity = commentConverter.fromDto(comment);
        if (commentEntity.getId() == null) {
            commentEntity.setAuthor(securityService.getCurrentUser());
            commentEntity.setTree(tree.get());
        }

        return commentRepository.save(commentEntity).getId();
    }

    @Transactional
    public Long edit(Long id, CommentDto updateTreeDto) {
        commentRepository.deleteById(id);
        updateTreeDto.setId(id);
        Comment commentEntity = commentConverter.fromDto(updateTreeDto);
        return commentRepository.save(commentEntity).getId();
    }

    public List<Comment> getAllByTreeId(Long treeId) {
        return commentRepository.findAllByTreeId(treeId);
    }

    public void deleteAllCommentByAuthor(Long authorId) { commentRepository.deleteAllByAuthorId(authorId); }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> getAllByAuthorId(Long authorId, Integer pageNumber, Integer step) {
        var page = PageRequest.of(pageNumber, step, Sort.by("creationDate"));
        return commentRepository.findAllByAuthorId(authorId, page);
    }
}
