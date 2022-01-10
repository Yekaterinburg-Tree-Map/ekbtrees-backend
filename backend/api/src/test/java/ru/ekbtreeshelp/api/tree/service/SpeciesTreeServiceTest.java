package ru.ekbtreeshelp.api.tree.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ekbtreeshelp.core.entity.SpeciesTree;
import ru.ekbtreeshelp.core.repository.SpeciesTreeRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpeciesTreeServiceTest {

    @InjectMocks
    private SpeciesTreeService speciesTreeService;

    @Mock
    private SpeciesTreeRepository speciesTreeRepository;

    @Test
    void save() {
        SpeciesTree speciesTree = new SpeciesTree();

        when(speciesTreeRepository.save(speciesTree)).thenReturn(speciesTree);

        speciesTreeService.save(speciesTree);

        verify(speciesTreeRepository, times(1)).save(speciesTree);
    }

    @Test
    void getAll() {
        speciesTreeService.getAll();

        verify(speciesTreeRepository, times(1)).findAll();
    }

    @Test
    void delete() {
        speciesTreeService.delete(1L);

        verify(speciesTreeRepository, times(1)).deleteById(1L);
    }
}