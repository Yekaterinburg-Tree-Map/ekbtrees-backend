package ru.naumen.ectmapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Транспортная сущность комментария")
@Data
@AllArgsConstructor
public class СommentDto {

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
