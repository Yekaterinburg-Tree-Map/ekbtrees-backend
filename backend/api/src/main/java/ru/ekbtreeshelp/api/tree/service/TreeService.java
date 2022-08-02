package ru.ekbtreeshelp.api.tree.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.ekbtreeshelp.api.file.service.FileService;
import ru.ekbtreeshelp.api.security.service.SecurityService;
import ru.ekbtreeshelp.api.tree.mapper.TreeMapper;
import ru.ekbtreeshelp.api.tree.dto.CreateTreeDto;
import ru.ekbtreeshelp.api.tree.dto.UpdateTreeDto;
import ru.ekbtreeshelp.core.entity.BaseEntity;
import ru.ekbtreeshelp.core.entity.FileEntity;
import ru.ekbtreeshelp.core.entity.SpeciesTree;
import ru.ekbtreeshelp.core.entity.Tree;
import ru.ekbtreeshelp.core.repository.CommentRepository;
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
    private final TreeMapper treeMapper;
    private final FileRepository fileRepository;
    private final FileService fileService;
    private final SpeciesTreeRepository speciesTreeRepository;
    private final SecurityService securityService;
    private final CommentRepository commentRepository;

    @Transactional
    public Long create(CreateTreeDto createTreeDto) {
        Long speciesId = createTreeDto.getSpeciesId();
        SpeciesTree speciesTree = null;
        if (speciesId != null) {
            Optional<SpeciesTree> speciesTreeOptional = speciesTreeRepository.findById(speciesId);
            if(speciesTreeOptional.isEmpty()) {
                throw new IllegalArgumentException("Species not found");
            }
            speciesTree = speciesTreeOptional.get();
        }

        Tree treeEntity = treeMapper.fromDto(createTreeDto);

        return createTree(createTreeDto.getFileIds(), speciesTree, treeEntity);
    }

    @Transactional
    public void update(Long id, UpdateTreeDto updateTreeDto) {
        Tree treeEntity = treeRepository.getOne(id);

        treeMapper.updateTreeFromDto(updateTreeDto, treeEntity);
    }

    private Long createTree(Collection<Long> fileIds, SpeciesTree speciesTree, Tree treeEntity) {
        if (treeEntity.getId() == null) {
            treeEntity.setAuthor(securityService.getCurrentUser());
        }
        treeEntity.setSpecies(speciesTree);

        Long id = treeRepository.save(treeEntity).getId();

        if (fileIds != null) {
            fileIds.forEach(fileId -> {
                FileEntity fileEntity = fileRepository.getOne(fileId);
                fileEntity.setTree(treeEntity);
                fileRepository.save(fileEntity);
            });
        }
        return id;
    }

    public Tree get(Long id) {
        return treeRepository.findById(id).orElseThrow();
    }

    public List<Tree> getAllByAuthorId(Long authorId, Integer pageNumber, Integer step) {
        var page = PageRequest.of(pageNumber, step, Sort.by("id").descending());
        return treeRepository.findAllByAuthorId(authorId, page);
    }

    public Page<Tree> listAll(Integer pageNumber, Integer step) {
        var page = PageRequest.of(pageNumber, step, Sort.by("id").descending());
        return treeRepository.findAll(page);
    }

    public void delete(Long id) {
        commentRepository.findAllByTreeId(id)
                .stream().map(BaseEntity::getId)
                .forEach(treeRepository::deleteById);
        treeRepository.deleteById(id);
    }

    public Long attachFile(Long treeId, MultipartFile file) {
        Long createdFileId = fileService.save(file).getId();
        fileRepository.updateTreeId(createdFileId, treeId);
        return createdFileId;
    }

    public void approve(Long treeId) {
        treeRepository.approve(treeId);
    }
}
