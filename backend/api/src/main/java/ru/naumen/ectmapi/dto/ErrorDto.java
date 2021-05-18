package ru.naumen.ectmapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorDto
{
    private final int statusCode;
    private final String message;
}
