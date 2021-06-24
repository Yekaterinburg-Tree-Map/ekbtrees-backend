package ru.ekbtreeshelp.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.ekbtreeshelp.api.entity.FileEntity;
import ru.ekbtreeshelp.api.repository.FileRepository;
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

    private static final String BUCKET = "ectm";

    private final FileRepository fileRepository;
    private final SecurityService securityService;
    private final AmazonS3 s3;

    public FileEntity save(MultipartFile file) {
        FileEntity fileEntity = saveToS3(file);
        fileEntity.setAuthor(securityService.getCurrentUser());
        return fileRepository.save(fileEntity);
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

    private FileEntity saveToS3(MultipartFile file) {

        String uri;
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

                uri = s3.getUrl(BUCKET, hash).toString();

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
