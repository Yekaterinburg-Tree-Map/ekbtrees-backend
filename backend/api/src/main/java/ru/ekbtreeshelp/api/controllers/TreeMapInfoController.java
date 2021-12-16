package ru.ekbtreeshelp.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.postgis.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ekbtreeshelp.api.converter.TreeMapInfoConverter;
import ru.ekbtreeshelp.api.dto.TreeMapInfoDto;
import ru.ekbtreeshelp.api.service.SecurityService;
import ru.ekbtreeshelp.api.service.TreeMapInfoService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "api/tree-map-info", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TreeMapInfoController {

    private final TreeMapInfoService treeMapInfoService;
    private final TreeMapInfoConverter treeMapInfoConverter;
    private final SecurityService securityService;

    @Operation(summary = "Предоставляет сокращенную информацию о деревьях, входящих в указанную область")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-in-region")
    @PreAuthorize("permitAll()")
    public List<TreeMapInfoDto> getInRegion(
            @RequestParam("x1") double topLeftLatitude,
            @RequestParam("y1") double topLeftLongitude,
            @RequestParam("x2") double bottomRightLatitude,
            @RequestParam("y2") double bottomRightLongitude
    ) {
        Point topLeftPoint = new Point(topLeftLatitude, topLeftLongitude);
        Point bottomRightPoint = new Point(bottomRightLatitude, bottomRightLongitude);
        return treeMapInfoConverter.toDto(treeMapInfoService.getInRegion(topLeftPoint, bottomRightPoint));
    }

    @Deprecated
    @Operation(summary = "Предоставляет сокращенную информацию о деревьях текущего пользователя")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get")
    @PreAuthorize("isAuthenticated()")
    public List<TreeMapInfoDto> get() {
        Long authorId = securityService.getCurrentUserId();
        return treeMapInfoConverter.toDto(treeMapInfoService.getAllByAuthorId(authorId, 0, 100));
    }

    @Operation(summary = "Предоставляет сокращенную информацию о деревьях текущего пользователя")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get/{page}/{size}")
    @PreAuthorize("isAuthenticated()")
    public List<TreeMapInfoDto> getCurrentUserTreeInfos(@PathVariable @Min(0) Integer page,
                                                        @PathVariable @Max(100) Integer size) {
        Long authorId = securityService.getCurrentUserId();
        return treeMapInfoConverter.toDto(treeMapInfoService.getAllByAuthorId(authorId, page, size));
    }
}
