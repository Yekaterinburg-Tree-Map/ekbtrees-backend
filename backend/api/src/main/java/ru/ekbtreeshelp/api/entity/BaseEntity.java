package ru.ekbtreeshelp.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(generator = "sequenceIdGenerator")
    @GenericGenerator(
            name = "sequenceIdGenerator",
            strategy = "sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY,
                            value = "true"
                    ),
                    @Parameter(
                            name = SequenceStyleGenerator.CONFIG_SEQUENCE_PER_ENTITY_SUFFIX,
                            value = "_id_seq"
                    )
            }
    )
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @CreatedDate
    private Date creationDate;

    @Column(name = "last_modification_date", nullable = false)
    @LastModifiedDate
    private Date lastModificationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseEntity other = (BaseEntity) o;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

}