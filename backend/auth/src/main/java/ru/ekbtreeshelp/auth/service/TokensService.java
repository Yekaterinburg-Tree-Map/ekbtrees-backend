package ru.ekbtreeshelp.auth.service;

import lombok.RequiredArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.stereotype.Service;
import ru.ekbtreeshelp.auth.dto.JwtDto;
import ru.ekbtreeshelp.auth.utils.CryptoUtils;
import ru.ekbtreeshelp.core.entity.RefreshToken;
import ru.ekbtreeshelp.core.entity.User;
import ru.ekbtreeshelp.core.repository.RefreshTokenRepository;
import ru.ekbtreeshelp.core.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TokensService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTService jwtService;
    private final CryptoUtils cryptoUtils;

    public JwtDto getTokensByCredentials(String email, String password) throws InvalidCredentialsException {
        User user = userRepository.findByEmail(email).orElseThrow(this::invalidLoginOrPassword);

        if (!user.getPassword().equals(cryptoUtils.sha256WithSalt(password))) {
            throw invalidLoginOrPassword();
        }

        return generateNewTokens(user);
    }

    public JwtDto getTokensByRefreshToken(String refreshToken) throws InvalidCredentialsException {

        User user;
        try {
            user = jwtService.extractUser(refreshToken, true);
        } catch (Exception e) {
            throw invalidRefreshToken(e);
        }

        refreshTokenRepository.getAllByUserId(user.getId())
                .stream()
                .filter((token -> token.getToken().equals(refreshToken)))
                .findFirst()
                .orElseThrow(this::invalidRefreshToken);

        return generateNewTokens(user);
    }

    private JwtDto generateNewTokens(User user) {
        JwtDto jwtDto = jwtService.generateNewPair(user);

        refreshTokenRepository.deleteAllByUser(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(jwtDto.getRefreshToken());
        refreshToken.setUser(user);

        refreshTokenRepository.save(refreshToken);

        return jwtDto;
    }

    public JwtDto getTokensByOAuth2(User user) {
        return generateNewTokens(user);
    }

    private InvalidCredentialsException invalidLoginOrPassword() {
        return new InvalidCredentialsException("Invalid login and/or password");
    }

    private InvalidCredentialsException invalidRefreshToken(Throwable cause) {
        return new InvalidCredentialsException(cause.getMessage(), cause);
    }

    private InvalidCredentialsException invalidRefreshToken() {
        return new InvalidCredentialsException("Token already revoked");
    }
}
