package ru.ekbtreeshelp.api.tree.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.ekbtreeshelp.api.file.service.FileService;
import ru.ekbtreeshelp.api.security.service.SecurityService;
import ru.ekbtreeshelp.api.tree.dto.CreateTreeDto;
import ru.ekbtreeshelp.api.tree.dto.UpdateTreeDto;
import ru.ekbtreeshelp.api.tree.mapper.TreeMapper;
import ru.ekbtreeshelp.core.entity.FileEntity;
import ru.ekbtreeshelp.core.entity.SpeciesTree;
import ru.ekbtreeshelp.core.entity.Tree;
import ru.ekbtreeshelp.core.repository.FileRepository;
import ru.ekbtreeshelp.core.repository.SpeciesTreeRepository;
import ru.ekbtreeshelp.core.repository.TreeRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TreeServiceTest {

    @InjectMocks
    TreeService treeService;

    @Mock
    private TreeRepository treeRepository;
    @Mock
    private TreeMapper treeMapper;
    @Mock
    private FileRepository fileRepository;
    @Mock
    private FileService fileService;
    @Mock
    private SpeciesTreeRepository speciesTreeRepository;
    @Mock
    private SecurityService securityService;

    @Test
    void when_speciesNotExist_then_throwException() {
        CreateTreeDto dto = new CreateTreeDto();
        dto.setSpeciesId(1L);
        assertThatThrownBy(() -> treeService.create(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Species not found");
    }

    @Test
    void when_speciesExist_then_createTree() {
        CreateTreeDto dto = new CreateTreeDto();
        dto.setSpeciesId(1L);
        dto.setFileIds(List.of(1L, 2L));
        Tree tree = new Tree();

        when(speciesTreeRepository.findById(dto.getSpeciesId())).thenReturn(Optional.of(new SpeciesTree()));
        when(treeMapper.fromDto(dto)).thenReturn(tree);
        when(treeRepository.save(tree)).thenReturn(tree);
        when(fileRepository.getOne(anyLong())).thenReturn(new FileEntity());

        treeService.create(dto);

        verify(speciesTreeRepository, times(1)).findById(dto.getSpeciesId());
        verify(treeMapper, times(1)).fromDto(dto);
        verify(securityService, times(1)).getCurrentUser();
        verify(treeRepository, times(1)).save(tree);
        verify(fileRepository, times(2)).getOne(anyLong());
        verify(fileRepository, times(2)).save(any(FileEntity.class));
    }

    @Test
    void update() {
        UpdateTreeDto updateTreeDto = new UpdateTreeDto();
        Tree tree = new Tree();
        tree.setId(1L);

        when(treeRepository.getOne(1L)).thenReturn(tree);

        treeService.update(1L, updateTreeDto);

        verify(treeMapper, times(1)).updateTreeFromDto(updateTreeDto, tree);
    }

    @Test
    void when_treeNotExist_then_throwException() {
        assertThatThrownBy(() -> treeService.get(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No value present");
    }

    @Test
    void when_treeExist_then_getTree() {
        Tree tree = new Tree();
        tree.setId(1L);

        when(treeRepository.findById(tree.getId())).thenReturn(Optional.of(new Tree()));

        treeService.get(1L);

        verify(treeRepository, times(1)).findById(1L);
    }

    @Test
    void getAllByAuthorId() {
        treeService.getAllByAuthorId(1L,1, 1);

        verify(treeRepository, times(1))
                .findAllByAuthorId(1L, PageRequest.of(1, 1, Sort.by("id").descending()));
    }

    @Test
    void listAll() {
        treeService.listAll(1, 1);

        verify(treeRepository, times(1))
                .findAll(PageRequest.of(1, 1, Sort.by("id").descending()));
    }

    @Test
    void delete() {
        treeService.delete(1L);
        verify(treeRepository, times(1)).deleteById(1L);
    }

    @Test
    void attachFile() {
        MultipartFile file = mock(MockMultipartFile.class);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setId(2L);
        Long treeId = 1L;

        when(fileService.save(file)).thenReturn(fileEntity);

        treeService.attachFile(treeId, file);

        verify(fileService, times(1)).save(file);
        verify(fileRepository, times(1)).updateTreeId(2L,1L);
    }
}