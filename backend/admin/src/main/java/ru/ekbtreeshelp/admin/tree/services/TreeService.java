package ru.ekbtreeshelp.admin.tree.services;

import ru.ekbtreeshelp.core.entity.Tree;

import java.util.List;

public interface TreeService {
    Tree create(Tree tree);
    Tree update(Tree tree);
    Tree get(Long id);
    List<Tree> listAll();
    void delete(Long id);
    List<Tree> search(String SomeValue);

}
