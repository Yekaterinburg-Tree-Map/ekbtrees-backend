package ru.ekbtreeshelp.api.service.exception;

public class FileServiceException extends RuntimeException {
    public FileServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
