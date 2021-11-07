package ru.ekbtreeshelp.admin.example.services;

import ru.ekbtreeshelp.core.entity.Example;

import java.util.List;

public interface ExampleService {
    Example create(Example example);
    Example update(Example example);
    Example get(Long id);
    List<Example> listAll();
    void delete(Long id);
    List<Example> search(String someValue);
}