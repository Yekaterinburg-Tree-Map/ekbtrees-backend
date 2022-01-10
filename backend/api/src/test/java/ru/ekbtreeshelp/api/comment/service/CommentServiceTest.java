package ru.ekbtreeshelp.api.comment.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ekbtreeshelp.api.comment.dto.CreateCommentDto;
import ru.ekbtreeshelp.api.comment.dto.UpdateCommentDto;
import ru.ekbtreeshelp.api.comment.mapper.CommentMapper;
import ru.ekbtreeshelp.api.security.service.SecurityService;
import ru.ekbtreeshelp.core.entity.Comment;
import ru.ekbtreeshelp.core.entity.User;
import ru.ekbtreeshelp.core.repository.CommentRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private SecurityService securityService;

    @Test
    void create() {
        CreateCommentDto createCommentDto = new CreateCommentDto();
        Comment comment = new Comment();

        when(commentMapper.fromDto(createCommentDto)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(comment);

        commentService.create(createCommentDto);

        verify(commentMapper, times(1)).fromDto(createCommentDto);
        verify(securityService, times(1)).getCurrentUser();
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void getAllByTreeId() {
        commentService.getAllByTreeId(1L);

        verify(commentRepository, times(1)).findAllByTreeId(1L);
    }

    @Test
    void update() {
        UpdateCommentDto dto = new UpdateCommentDto();
        Comment comment = new Comment();
        comment.setId(1L);

        when(commentRepository.getOne(comment.getId())).thenReturn(comment);

        commentService.update(comment.getId(), dto);

        verify(commentMapper, times(1)).updateFromDto(dto, comment);
    }

    @Test
    void delete() {
        commentService.delete(1L);

        verify(commentRepository, times(1)).deleteById(1L);
    }
}