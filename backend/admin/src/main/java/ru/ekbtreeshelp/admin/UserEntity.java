package ru.ekbtreeshelp.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Table;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(appliesTo = "users")
public class UserEntity {

    @Id
    private Long id;

    private String name;
    private String login;
    private Date registryDate;

    private boolean blocked;
}
