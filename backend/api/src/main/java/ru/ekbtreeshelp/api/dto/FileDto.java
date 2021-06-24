package ru.ekbtreeshelp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Транспортная сущность файла")
public class FileDto {

    @Schema(description = "Идентификатор файла")
    private Long id;

    @Schema(description = "Название файла")
    private String title;

    @Schema(description = "Тип файла")
    private String mimeType;

    @Schema(description = "Размер файла")
    private Long size;

    @Schema(description = "Ссылка, переход по которой инициирует загрузку контента файла")
    private String uri;

    @Schema(description = "Идентификатор дерева, с которым связан файл")
    private Long treeId;

}
