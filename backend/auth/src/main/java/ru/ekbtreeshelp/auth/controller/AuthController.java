package ru.ekbtreeshelp.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.ekbtreeshelp.auth.constants.CookieNames;
import ru.ekbtreeshelp.auth.dto.NewUserDto;
import ru.ekbtreeshelp.auth.service.TokensService;
import ru.ekbtreeshelp.auth.service.UserService;
import ru.ekbtreeshelp.auth.utils.CookieUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOG = LogManager.getLogger(AuthController.class);

    private final UserService userService;
    private final TokensService tokensService;

    @Operation(summary = "Регистрирует нового пользователя")
    @PostMapping(value = "/register")
    public void registerUser(@RequestBody NewUserDto newUserDto) {
        userService.registerNewUser(newUserDto);
    }

    @Operation(summary = "Производит аутентификацию по почте и паролю, " +
            "токены возвращаются в виде Cookie (AccessToken, RefreshToken)")
    @PostMapping("/login")
    public void login(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, HttpServletResponse response) {
        String encodedCredentials = authHeader.replace("Basic ", "");
        String[] decodedCredentials = new String(Base64.getUrlDecoder().decode(encodedCredentials)).split(":");

        String email = decodedCredentials[0];
        String password = decodedCredentials[1];

        try {
            CookieUtils.setTokenCookies(response, tokensService.getTokensByCredentials(email, password));
        } catch (InvalidCredentialsException e) {
            LOG.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        }
    }

    @Operation(summary = "Получение новой пары Cookie (AccessToken, RefreshToken) по старому RefreshToken")
    @PostMapping("/newTokens")
    public void newToken(@CookieValue(CookieNames.REFRESH_TOKEN) String oldRefreshToken, HttpServletResponse response) {
        try {
            CookieUtils.setTokenCookies(response, tokensService.getTokensByRefreshToken(oldRefreshToken));
        } catch (InvalidCredentialsException e) {
            LOG.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        }
    }

    @Operation(summary = "Производит необходимые действия для выхода пользователя из системы")
    @PostMapping("/logout")
    public void logout(@CookieValue(CookieNames.ACCESS_TOKEN) String accessToken,
                       @CookieValue(CookieNames.REFRESH_TOKEN) String refreshToken,
                       HttpServletResponse response) {
        try {
            tokensService.deleteTokens(accessToken, refreshToken);
        } catch (InvalidCredentialsException e) {
            LOG.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        }
        CookieUtils.removeCookies(response, CookieNames.ACCESS_TOKEN, CookieNames.REFRESH_TOKEN);
    }
}
