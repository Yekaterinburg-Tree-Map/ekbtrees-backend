package ru.ekbtreeshelp.api.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorDto {

    private final int statusCode;

    private final String message;

}
