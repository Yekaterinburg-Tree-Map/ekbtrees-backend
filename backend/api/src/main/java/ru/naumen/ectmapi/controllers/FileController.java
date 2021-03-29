package ru.naumen.ectmapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("api/file")
public class FileController {

    @Operation(
            summary = "Загружает файл в хранилище",
            responses = {@ApiResponse(description = "id загружаемого файла")}
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/upload")
    public UUID upload(MultipartFile file){
        return UUID.randomUUID();
    }

    @Operation(summary = "Предоставляет файл по id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/download/{id}")
    public void download(@PathVariable UUID id, HttpServletResponse response){

    }

    @Operation(summary = "Удаляет файл по id")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable UUID id){

    }
}
