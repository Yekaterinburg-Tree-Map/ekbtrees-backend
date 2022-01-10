package ru.ekbtreeshelp.api.security.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import ru.ekbtreeshelp.api.security.JWTUserDetails;
import ru.ekbtreeshelp.core.entity.User;
import ru.ekbtreeshelp.core.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {

    @InjectMocks
    SecurityService securityService;

    @Mock
    private  UserRepository userRepository;

    @Test
    void when_currentUserNotAuthentication_then_throwException() {
        assertThatThrownBy(() -> securityService.getCurrentUser())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("There is no current authentication!");
    }


    @Test
    void when_currentUserIdExist_then_getCurrentUser() {
        JWTUserDetails userDetails = new JWTUserDetails();
        userDetails.setId(1L);
        Optional<User> user = Optional.of(new User());

        SecurityContextHolder
                .setContext(new SecurityContextImpl(new TestingAuthenticationToken(userDetails, null)));
        when(userRepository.findById(userDetails.getId())).thenReturn(user);

        securityService.getCurrentUser();

        verify(userRepository, times(1)).findById(userDetails.getId());
    }
}