package ru.naumen.ectmapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.naumen.ectmapi.dto.BaseErrorDto;
import ru.naumen.ectmapi.dto.BaseSuccessDto;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestControllerAdvice
public class BaseResponseBodyAdvice extends ResponseEntityExceptionHandler implements ResponseBodyAdvice<Object> {

    private static final Logger LOG = LoggerFactory.getLogger(BaseResponseBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> selectedConverterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body instanceof BaseErrorDto) {
            return body;
        }
        if (request.getURI().toString().contains("/api-docs")) {
            return body;
        }
        return new BaseSuccessDto<>(body);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request, HttpServletResponse response) {
        return handleExceptionInternal(
                exception, null, new HttpHeaders(),
                Objects.requireNonNull(HttpStatus.resolve(response.getStatus())), request
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOG.error(ex.getMessage(), ex);
        return super.handleExceptionInternal(ex, new BaseErrorDto(ex.getMessage()), headers, status, request);
    }
}
