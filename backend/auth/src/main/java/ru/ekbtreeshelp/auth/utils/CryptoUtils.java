package ru.ekbtreeshelp.auth.utils;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import ru.ekbtreeshelp.auth.config.CryptoConfig;

@Component
@RequiredArgsConstructor
public class CryptoUtils {

    private final CryptoConfig cryptoConfig;

    public String sha256WithSalt(String inputString) {
        return DigestUtils.sha256Hex(inputString + cryptoConfig.getSalt());
    }
}
