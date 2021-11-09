package ru.ekbtreeshelp.admin.tree.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ekbtreeshelp.core.entity.Tree;
import ru.ekbtreeshelp.core.repository.TreeRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TreeServiceImpl implements  TreeService{

    private final TreeRepository treeRepository;

    @Override
    public Tree create(Tree tree)
    {
        return treeRepository.save(tree);
    }

    public Tree update(Tree tree)
    {
        if(tree.getId() == null)
            throw new IllegalArgumentException("id can't be null");
        return treeRepository.save(tree);
    }

    public Tree get(Long id)
    {
        return treeRepository.getOne(id);
    }

    public List<Tree> listAll()
    {
        return treeRepository.findAll();
    }

    public void delete(Long id)
    {
        treeRepository.delete(treeRepository.getOne(id));
    }

    public  List<Tree> search(String someValue)
    {
        if (someValue == null || someValue.isEmpty())
            return Collections.emptyList();
        return treeRepository.findAllBySomeValue(someValue);
    }
}
