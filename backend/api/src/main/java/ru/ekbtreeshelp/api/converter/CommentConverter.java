package ru.ekbtreeshelp.api.converter;

import org.mapstruct.Mapper;
import ru.ekbtreeshelp.api.dto.CommentDto;
import ru.ekbtreeshelp.api.entity.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentConverter {

    CommentDto toDto(Comment comment);

    List<CommentDto> toDto(List<Comment> comments);

    Comment fromDto(CommentDto commentDto);
}
