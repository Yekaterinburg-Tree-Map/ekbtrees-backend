package ru.ekbtreeshelp.admin.example.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ekbtreeshelp.admin.example.dtos.CreateNewExampleDto;
import ru.ekbtreeshelp.admin.example.dtos.ExampleResponseDto;
import ru.ekbtreeshelp.admin.example.dtos.UpdateExampleDto;
import ru.ekbtreeshelp.admin.example.mappers.ExampleMapper;
import ru.ekbtreeshelp.admin.example.services.ExampleService;
import ru.ekbtreeshelp.core.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/example")
@RequiredArgsConstructor
public class ExampleController {

    private final ExampleService exampleService;
    private final ExampleMapper exampleMapper;

    @PostMapping
    ExampleResponseDto createExample(@RequestBody CreateNewExampleDto createNewExampleDto) {
        Example newExample = exampleService.create(exampleMapper.fromRequestDto(createNewExampleDto));
        return exampleMapper.toResponseDto(newExample);
    }

    @GetMapping("/{id}")
    ExampleResponseDto getById(@PathVariable Long id) {
        Example requestedExample = exampleService.get(id);
        return exampleMapper.toResponseDto(requestedExample);
    }

    @GetMapping
    List<ExampleResponseDto> listAll() {
        List<Example> requestedExamples = exampleService.listAll();
        return requestedExamples.stream()
                .map(exampleMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    List<ExampleResponseDto> searchExamples(@RequestParam String someValue) {
        List<Example> requestedExamples = exampleService.search(someValue);
        return requestedExamples.stream()
                .map(exampleMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @PutMapping
    ExampleResponseDto updateExample(@RequestBody UpdateExampleDto updateExampleDto) {
        Example editedExample = exampleService.update(exampleMapper.fromRequestDto(updateExampleDto));
        return exampleMapper.toResponseDto(editedExample);
    }

    @DeleteMapping("/{id}")
    void deleteExample(@PathVariable Long id) {
        exampleService.delete(id);
    }
}
