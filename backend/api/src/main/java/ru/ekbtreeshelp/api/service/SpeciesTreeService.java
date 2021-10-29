package ru.ekbtreeshelp.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ekbtreeshelp.core.entity.SpeciesTree;
import ru.ekbtreeshelp.core.repository.SpeciesTreeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeciesTreeService {

    private final SpeciesTreeRepository speciesTreeRepository;

    public Long save(SpeciesTree speciesTree) {
        return speciesTreeRepository.save(speciesTree).getId();
    }

    public List<SpeciesTree> getAll() {
        return speciesTreeRepository.findAll();
    }

    public void delete(Long id) {
        speciesTreeRepository.deleteById(id);
    }
}
