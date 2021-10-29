package ru.ekbtreeshelp.core.entity;

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
@Table(name = "comments")
public class Comment extends BaseEntity {

    /**
     * Автор комментария
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    /**
     * Текст комментария
     */
    @Column
    private String text;

    /**
     * Дерево, к которому относится комментарий
     */
    @ManyToOne
    @JoinColumn(name = "tree_id", nullable = false)
    private Tree tree;
}
