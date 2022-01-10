package ru.ekbtreeshelp.api.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.ekbtreeshelp.api.security.service.SecurityService;
import ru.ekbtreeshelp.core.entity.FileEntity;
import ru.ekbtreeshelp.core.repository.FileRepository;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @InjectMocks
    FileService fileService;

    private static final String BUCKET = "treeshelp";
    @Mock
    private FileRepository fileRepository;
    @Mock
    private SecurityService securityService;
    @Mock
    private AmazonS3 s3;

    @Test
    void save() {
        MultipartFile file = mock(MockMultipartFile.class);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setTitle(file.getOriginalFilename());
        fileEntity.setMimeType(file.getContentType());
        fileEntity.setUri("/api/file/download/" + fileEntity.getId());

        when(fileRepository.save(any(FileEntity.class))).thenReturn(fileEntity);

        fileService.save(file);

        verify(securityService, times(1)).getCurrentUser();
        verify(fileRepository, times(1)).save(any(FileEntity.class));
    }

    @Test
    void when_fileNotExist_then_throwException() {
        assertThatThrownBy(() -> fileService.get(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No value present");
    }

    @Test
    void when_fileExist_then_getFile() {
        FileEntity file = new FileEntity();
        file.setId(1L);

        when(fileRepository.findById(file.getId())).thenReturn(Optional.of(file));

        fileService.get(file.getId());

        verify(fileRepository, times(1)).findById(file.getId());
    }

    @Test
    void listByTreeId() {
        fileService.listByTreeId(1L);

        verify(fileRepository, times(1)).findAllByTreeId(1L);
    }

    @Test
    void when_fileNotExistForDelete_then_throwException() {
        assertThatThrownBy(() -> fileService.delete(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No value present");
    }

    @Test
    void delete() {
        FileEntity fileToDelete = new FileEntity();

        when(fileRepository.findById(1L)).thenReturn(Optional.of(fileToDelete));

        fileService.delete(1L);

        verify(fileRepository, times(1)).delete(fileToDelete);
    }

    @Test
    void getFromS3() {
        FileEntity fileEntity = new FileEntity();
        GetObjectRequest fileRequest = new GetObjectRequest(BUCKET, fileEntity.getHash());
        S3Object file = new S3Object();
        file.setObjectContent(InputStream.nullInputStream());

        when(s3.getObject(fileRequest)).thenReturn(file);

        fileService.getFromS3(fileEntity);

        verify(s3, times(1)).getObject(fileRequest);
    }

}