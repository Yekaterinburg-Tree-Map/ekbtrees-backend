package ru.ekbtreeshelp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Транспортная сущность комментария")
public class CommentDto {

    @Schema(description = "Идентификатор комментария")
    private Long id;

    @Schema(description = "Дата и время добавления комментария")
    private Instant created;

    @Schema(description = "Ссылка на автора")
    private Long authorId;

    @Schema(description = "Текст комментария")
    private String text;

    @Schema(description = "Идентификатор дерева, к которому относиться комментарий")
    private Long treeId;
}
