package ru.ekbtreeshelp.admin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserData {
    String name;
    String login;
    Date registryDate;
    Long id;
    boolean blocked;
}
