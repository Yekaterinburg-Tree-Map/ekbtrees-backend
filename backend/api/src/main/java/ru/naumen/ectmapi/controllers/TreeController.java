package ru.naumen.ectmapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.naumen.ectmapi.dto.TreeDto;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "api/tree", produces = MediaType.APPLICATION_JSON_VALUE)
public class TreeController {

    @Operation(summary = "Сохраняет дерево")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save")
    public void save(@RequestBody @Valid TreeDto treeDto){

    }

    @Operation(summary = "Предоставляет дерево по id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get/{id}")
    public TreeDto get(@PathVariable Long id) {
        return new TreeDto();
    }

    @Operation(summary = "Изменяет информацию о дереве")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update")
    public void update(@RequestBody @Valid TreeDto treeDto){
        
    }

    @Operation(summary = "Удаляет дерево по id")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){

    }

}
