package ru.ekbtreeshelp.admin.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateExampleDto {
    Long id;
    String someValue;
}
