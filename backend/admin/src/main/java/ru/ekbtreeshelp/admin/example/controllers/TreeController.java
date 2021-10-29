package ru.ekbtreeshelp.admin.example.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ekbtreeshelp.admin.example.dtos.CreateNewExampleDto;
import ru.ekbtreeshelp.admin.example.dtos.ExampleResponseDto;
import ru.ekbtreeshelp.admin.example.dtos.UpdateTreeDto;
import ru.ekbtreeshelp.admin.example.mappers.TreeMapper;
import ru.ekbtreeshelp.admin.example.services.TreeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/tree")
@RequiredArgsConstructor
public class TreeController {
    private final TreeService treeService;
    private final TreeMapper treeMapper;

    @PostMapping
    ExampleResponseDto createTree(@RequestBody CreateNewExampleDto createNewTreeDto) {
        Tree newTree = treeService.create(treeMapper.fromRequestDto(createNewTreeDto));
        return treeMapper.toResponseDto(newTree);
    }

    @GetMapping("/{id}")
    ExampleResponseDto getById(@PathVariable Long id) {
        Tree requestedExample = treeService.get(id);
        return treeMapper.toResponseDto(requestedTree);
    }

    @GetMapping
    List<ExampleResponseDto> listAll() {
        List<Tree> requestedTrees = treeService.listAll();
        return requestedTrees.stream()
                .map(treeMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    List<ExampleResponseDto> searchTrees(@RequestParam String someValue) {
        List<Tree> requestedTrees = treeService.search(someValue);
        return requestedTrees.stream()
                .map(treeMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @PutMapping
    ExampleResponseDto updateTree(@RequestBody UpdateTreeDto updateTreeDto) {
        Tree editedTree = treeService.update(treeMapper.fromRequestDto(updateTreeDto));
        return treeMapper.toResponseDto(editedTree);
    }

    @DeleteMapping("/{id}")
    void deleteTree(@PathVariable Long id) {
        treeService.delete(id);
    }
}
