package ru.ekbtreeshelp.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ekbtreeshelp.api.config.JWTConfig;

@Component
@RequiredArgsConstructor
public class JWTProvider {

    private final JWTConfig config;

    public JWTUserDetails getUserDetailsFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(config.getAccessTokenSecret()).parseClaimsJws(token);
        return JWTUserDetails.fromParsedTokenPayload(claimsJws.getBody());
    }
}
