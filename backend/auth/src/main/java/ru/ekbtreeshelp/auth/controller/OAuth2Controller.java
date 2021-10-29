package ru.ekbtreeshelp.auth.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.ekbtreeshelp.auth.config.OAuth2Config;
import ru.ekbtreeshelp.auth.service.oauth2.OAuth2Service;
import ru.ekbtreeshelp.core.entity.Provider;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/auth/oauth2")
public class OAuth2Controller {

    private final OAuth2Config oAuth2Config;
    private final Map<Provider, OAuth2Service> providersRegistry;

    @Autowired
    OAuth2Controller(@Qualifier("providersRegistry") Map<Provider, OAuth2Service> providersRegistry,
                     OAuth2Config oAuth2Config) {
        this.providersRegistry = providersRegistry;
        this.oAuth2Config = oAuth2Config;
    }

    @Operation(summary = "Производит аутентификацию пользователя с помощью стороннего провайдера")
    @GetMapping("/{provider}")
    void handleAuthRequest(@PathVariable Provider provider, HttpServletResponse response) throws IOException {
        response.sendRedirect(getService(provider).getOAuthUri());
    }

    @Hidden
    @GetMapping("/{provider}/callback")
    void handleCallback(@PathVariable Provider provider,
                        @RequestParam("code") String authorizationCode,
                        HttpServletResponse response) throws IOException {

        getService(provider).handleOAuthFlow(authorizationCode, response);
        response.sendRedirect(oAuth2Config.getBaseUrl());
    }

    private OAuth2Service getService(Provider provider) {
        OAuth2Service oAuth2Service = providersRegistry.get(provider);
        if (oAuth2Service == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown provider");
        }
        return oAuth2Service;
    }

}
