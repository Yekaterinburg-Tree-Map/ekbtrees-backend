package ru.naumen.ectmapi.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.naumen.ectmapi.dto.ErrorDto;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Hidden
@RestController
public class CustomErrorController implements ErrorController
{
    private static final String ERROR_PATH = "/api/error";

    @RequestMapping(ERROR_PATH)
    ErrorDto handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
            return new ErrorDto(httpStatus.value(), httpStatus.getReasonPhrase());
        }

        return new ErrorDto(0, "Unknown error");
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
