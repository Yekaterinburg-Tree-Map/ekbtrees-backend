package ru.naumen.ectmapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.ectmapi.entity.Tree;
import ru.naumen.ectmapi.repository.SpeciesTreeRepository;
import ru.naumen.ectmapi.repository.TreeRepository;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TreeService {

    private final TreeRepository treeRepository;
    private final SpeciesTreeRepository speciesTreeRepository;

    public void save(Tree tree){
        if(!speciesTreeRepository.isExists(tree.getSpecies().getId())) {
            throw new IllegalStateException("Species not found");
        }

        if (tree.isNew()) {
            treeRepository.create(tree);
        } else {
            treeRepository.update(tree);
        }
    }

    public Tree get(Long id){
        return treeRepository.find(id);
    }

    public void delete(Long id) {
        treeRepository.delete(id);
    }
}
