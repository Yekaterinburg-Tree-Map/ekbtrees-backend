package ru.naumen.ectmapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.naumen.ectmapi.entity.SpeciesTree;
import ru.naumen.ectmapi.repository.SpeciesTreeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeciesTreeService {

    private final SpeciesTreeRepository speciesTreeRepository;

    public void save(SpeciesTree speciesTree) {
        speciesTreeRepository.create(speciesTree);
    }

    public List<SpeciesTree> getAll() {
        return speciesTreeRepository.findAll();
    }

    public void delete(Long id) {
        speciesTreeRepository.delete(id);
    }
}
