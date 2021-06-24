package ru.ekbtreeshelp.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.postgis.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ekbtreeshelp.api.dto.TreeClusterDto;
import ru.ekbtreeshelp.api.service.TreeMapInfoService;

import java.util.List;

@RestController
@RequestMapping(value = "api/trees-cluster", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TreesClusterController {

    private final TreeMapInfoService treeService;

    @Operation(summary = "Предоставляет информацию о кластерах деревьев, входящих в указанную область")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-in-region")
    public List<TreeClusterDto> getInRegion(@RequestParam("x1") double topLeftLatitude,
                                            @RequestParam("y1") double topLeftLongitude,
                                            @RequestParam("x2") double bottomRightLatitude,
                                            @RequestParam("y2") double bottomRightLongitude) {

        Point topLeftPoint = new Point(topLeftLatitude, topLeftLongitude);
        Point bottomRightPoint = new Point(bottomRightLatitude, bottomRightLongitude);
        return treeService.getClustersInRegion(topLeftPoint, bottomRightPoint);

    }
}
