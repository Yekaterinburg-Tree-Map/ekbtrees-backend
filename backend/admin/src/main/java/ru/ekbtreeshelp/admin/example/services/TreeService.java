package ru.ekbtreeshelp.admin.example.services;

import io.swagger.v3.oas.models.examples.Example;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ekbtreeshelp.core.entity.Tree;
import ru.ekbtreeshelp.core.repository.TreeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreeService {
    private final TreeRepository treeRepository;

    public Tree create(Tree tree){return treeRepository.save(tree)}

    public Tree update(Tree tree) {
        if (tree.getId() == null) {
            throw new IllegalArgumentException("Id can't be null!");
        }
        return TreeRepository.save(tree);
    }

    public Tree get(Long id) {
        return treeRepository.getOne(id);
    }

    public List<Tree> listAll() {
        return treeRepository.findAll();
    }

    public void delete(Long id) {
        exampleRepository.deleteById(id);
    }

    public List<Tree> search(String someValue) {
        if (someValue == null || someValue.isEmpty()) {
            return List.of();
        }
        return treeRepository.findAllBySomeValue(someValue);
    }
}
