package ru.ekbtreeshelp.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.ekbtreeshelp.api.converter.TreeConverter;
import ru.ekbtreeshelp.api.dto.CreateTreeDto;
import ru.ekbtreeshelp.api.dto.TreeDto;
import ru.ekbtreeshelp.core.entity.SpeciesTree;
import ru.ekbtreeshelp.core.entity.Tree;
import ru.ekbtreeshelp.core.repository.FileRepository;
import ru.ekbtreeshelp.core.repository.SpeciesTreeRepository;
import ru.ekbtreeshelp.core.repository.TreeRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TreeService {

    private final TreeRepository treeRepository;
    private final TreeConverter treeConverter;
    private final FileRepository fileRepository;
    private final FileService fileService;
    private final SpeciesTreeRepository speciesTreeRepository;
    private final SecurityService securityService;

    @Deprecated
    @Transactional
    public Long save(TreeDto tree) {

        Optional<SpeciesTree> speciesTree = speciesTreeRepository.findById(tree.getSpecies().getId());
        if(speciesTree.isEmpty()) {
            throw new IllegalArgumentException("Species not found");
        }

        Tree treeEntity = treeConverter.fromDto(tree);

        return createTree(tree.getFileIds(), speciesTree.get(), treeEntity);
    }

    @Transactional
    public Long create(CreateTreeDto createTreeDto) {
        Optional<SpeciesTree> speciesTree = speciesTreeRepository.findById(createTreeDto.getSpeciesId());
        if(speciesTree.isEmpty()) {
            throw new IllegalArgumentException("Species not found");
        }

        Tree treeEntity = treeConverter.fromDto(createTreeDto);

        return createTree(createTreeDto.getFileIds(), speciesTree.get(), treeEntity);
    }

    private Long createTree(Collection<Long> fileIds, SpeciesTree speciesTree, Tree treeEntity) {
        if (treeEntity.getId() == null) {
            treeEntity.setAuthor(securityService.getCurrentUser());
        }
        treeEntity.setSpecies(speciesTree);

        Long id = treeRepository.save(treeEntity).getId();

        if (fileIds != null) {
            fileIds.forEach(fileId -> fileRepository.updateTreeId(fileId, treeEntity.getId()));
        }
        return id;
    }

    public Tree get(Long id) {
        return treeRepository.findById(id).orElseThrow();
    }

    public List<Tree> getAllByAuthorId(Long authorId) {
        return treeRepository.findAllByAuthorId(authorId);
    }

    public void delete(Long id) {
        treeRepository.deleteById(id);
    }

    public Long attachFile(Long treeId, MultipartFile file) {
        Long createdFileId = fileService.save(file).getId();
        fileRepository.updateTreeId(createdFileId, treeId);
        return createdFileId;
    }
}
