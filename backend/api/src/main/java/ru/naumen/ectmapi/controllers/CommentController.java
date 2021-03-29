package ru.naumen.ectmapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.naumen.ectmapi.dto.СommentDto;

@RestController
@RequestMapping(value = "api/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

    @Operation(summary = "Сохраняет комментарий")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save")
    public void save(@RequestBody СommentDto commentDto){

    }

    @Operation(summary = "Удаляет комментарий по id")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){

    }
}
