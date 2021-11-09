package ru.ekbtreeshelp.admin.tree.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ekbtreeshelp.admin.tree.dtos.CreateNewTreeDto;
import ru.ekbtreeshelp.admin.tree.dtos.TreeResponseDto;
import ru.ekbtreeshelp.admin.tree.dtos.UpdateTreeDto;
import ru.ekbtreeshelp.admin.tree.mappers.TreeMapper;
import ru.ekbtreeshelp.admin.tree.services.TreeService;
import ru.ekbtreeshelp.core.entity.Tree;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/tree")
@RequiredArgsConstructor
public class TreeController {
    private final TreeService treeService;
    private final TreeMapper treeMapper;

    @PostMapping
    TreeResponseDto createTree(@RequestBody CreateNewTreeDto createNewTreeDto)
    {
        Tree newTree = treeService.create(treeMapper.fromRequestDto(createNewTreeDto));
        return  treeMapper.toResponseDto(newTree);
    }

    @GetMapping("/{id}")
    TreeResponseDto getById(@PathVariable Long id)
    {
        Tree requestedTree = treeService.get(id);
        return  treeMapper.toResponseDto(requestedTree);
    }

    @GetMapping
    List<TreeResponseDto> getAll()
    {
        List<Tree> list = treeService.listAll();
        return list.stream()
                .map(treeMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @PutMapping
    TreeResponseDto updateTree(@RequestBody UpdateTreeDto updateTreeDto)
    {
        Tree editedTree = treeService.update(treeMapper.fromRequestDto(updateTreeDto));
        return treeMapper.toResponseDto(editedTree);
    }

    @DeleteMapping
    void deleteTree(@PathVariable Long id)
    {
        treeService.delete(id);
    }
}
