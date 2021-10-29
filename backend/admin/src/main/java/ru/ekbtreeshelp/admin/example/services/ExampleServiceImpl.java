package ru.ekbtreeshelp.admin.example.services;

import io.swagger.v3.oas.models.examples.Example;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ekbtreeshelp.core.entity.Example;
import ru.ekbtreeshelp.core.repository.ExampleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExampleServiceImpl implements ExampleService {

    private final ExampleRepository exampleRepository;

    @Override
    public Example create(Example example) {
        return exampleRepository.save(example);
    }

    @Override
    public Example update(Example example) {
        if (example.getId() == null) {
            throw new IllegalArgumentException("Id can't be null!");
        }
        return exampleRepository.save(example);
    }

    @Override
    public Example get(Long id) {
        return exampleRepository.getOne(id);
    }

    @Override
    public List<Example> listAll() {
        return exampleRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        exampleRepository.deleteById(id);
    }

    @Override
    public List<Example> search(String someValue) {
        if (someValue == null || someValue.isEmpty()) {
            return List.of();
        }
        return exampleRepository.findAllBySomeValue(someValue);
    }
}
