package ru.naumen.ectmapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.naumen.ectmapi.dto.GeographicalPointDto;
import ru.naumen.ectmapi.dto.TreeMapInfoDto;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "api/treeMapInfo", produces = MediaType.APPLICATION_JSON_VALUE)
public class TreeMapInfoController {

    @Operation(summary = "Предоставляет информацию о деревьях, входящих в указаную область")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get")
    public List<TreeMapInfoDto> get(
            @Valid @RequestParam("topLeft") GeographicalPointDto topLeft,
            @Valid @RequestParam("bottomRight") GeographicalPointDto bottomRight
    ) {
        return Collections.singletonList(new TreeMapInfoDto());
    }
}
