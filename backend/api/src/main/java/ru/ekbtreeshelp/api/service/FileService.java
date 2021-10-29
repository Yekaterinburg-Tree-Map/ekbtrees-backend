package ru.ekbtreeshelp.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.ekbtreeshelp.core.entity.FileEntity;
import ru.ekbtreeshelp.core.repository.FileRepository;
import ru.ekbtreeshelp.api.service.exception.FileServiceException;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileService.class);
    private static final String BUCKET = "ectm";

    private final FileRepository fileRepository;
    private final SecurityService securityService;
    private final AmazonS3 s3;

    @Transactional
    public FileEntity save(MultipartFile file) {
        FileEntity fileEntity = saveToS3(file);
        fileEntity.setAuthor(securityService.getCurrentUser());
        fileEntity = fileRepository.save(fileEntity);
        if (fileEntity.getUri() == null) {
            fileEntity.setUri("/api/file/download/" + fileEntity.getId());
            fileEntity = fileRepository.save(fileEntity);
        }
        return fileEntity;
    }

    public FileEntity get(Long id) {
        return fileRepository.findById(id).orElseThrow();
    }

    public List<FileEntity> listByTreeId(Long treeId) {
        return fileRepository.findAllByTreeId(treeId);
    }

    public void delete(Long id) {
        FileEntity fileToDelete = fileRepository.findById(id).orElseThrow();

        if (fileRepository.countByHash(fileToDelete.getHash()) == 1) {
            deleteFromS3(fileToDelete);
        }

        fileRepository.delete(fileToDelete);
    }

    public ByteArrayResource getFromS3(FileEntity fileEntity) {
        GetObjectRequest fileRequest = new GetObjectRequest(BUCKET, fileEntity.getHash());
        try {
            byte[] fileBytes = s3.getObject(fileRequest).getObjectContent().readAllBytes();
            return new ByteArrayResource(fileBytes);
        } catch (IOException e) {
            throw new FileServiceException(e.getMessage(), e);
        }
    }

    private FileEntity saveToS3(MultipartFile file) {

        String uri = null;
        String hash;
        long fileSize;

        File tempFile = null;

        try(InputStream inputStream = file.getInputStream()) {

            tempFile = File.createTempFile(UUID.randomUUID().toString(), "tmp");
            FileUtils.copyInputStreamToFile(inputStream, tempFile);

            fileSize = tempFile.length();
            hash = DatatypeConverter.printHexBinary(
                    DigestUtils.md5Digest(FileUtils.readFileToByteArray(tempFile))
            ).toUpperCase();

            Optional<FileEntity> sameHashFile = fileRepository.findFirstByHash(hash);

            if (sameHashFile.isEmpty()) {

                s3.putObject(
                        new PutObjectRequest(BUCKET, hash, tempFile)
                                .withCannedAcl(CannedAccessControlList.PublicRead)
                );

            } else {
                uri = sameHashFile.get().getUri();
            }

        } catch (Exception e) {
            throw new FileServiceException(e.getMessage(), e);
        } finally {
            if (tempFile != null) {
                try {
                    FileUtils.forceDelete(tempFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        FileEntity fileEntity = new FileEntity();
        fileEntity.setTitle(file.getOriginalFilename());
        fileEntity.setMimeType(file.getContentType());
        fileEntity.setSize(fileSize);
        fileEntity.setUri(uri);
        fileEntity.setHash(hash);

        return fileEntity;
    }

    private void deleteFromS3(FileEntity fileToDelete) {
        s3.deleteObject(BUCKET, fileToDelete.getHash());
    }
}
