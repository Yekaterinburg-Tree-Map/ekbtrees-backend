package ru.ekbtreeshelp.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files")
public class FileEntity extends BaseEntity {

    /**
     * Название файла
     */
    @Column
    private String title;

    /**
     * Тип файла
     */
    @Column(name = "mime_type")
    private String mimeType;

    /**
     * Размер файла
     */
    @Column
    private Long size;

    /**
     * Ссылка, переход по которой инициирует загрузку контента файла
     */
    @Column
    private String uri;

    /**
     * Хэш файла
     */
    @Column
    private String hash;

    /**
     * Дерево, к которому привязан файл
     */
    @ManyToOne
    @JoinColumn(name = "tree_id")
    private Tree tree;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
}
