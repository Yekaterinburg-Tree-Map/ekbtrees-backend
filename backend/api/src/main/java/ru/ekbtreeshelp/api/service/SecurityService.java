package ru.ekbtreeshelp.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.ekbtreeshelp.api.entity.User;
import ru.ekbtreeshelp.api.repository.UserRepository;
import ru.ekbtreeshelp.api.security.JWTUserDetails;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        return userRepository.findById(getCurrentUserId()).orElseThrow();
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("There is no current authentication!");
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof JWTUserDetails)) {
            throw new IllegalArgumentException("Unknown authentication, can't extract user id!");
        }
        return ((JWTUserDetails) principal).getId();
    }

}
