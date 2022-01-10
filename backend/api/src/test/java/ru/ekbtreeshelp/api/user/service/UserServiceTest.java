package ru.ekbtreeshelp.api.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ekbtreeshelp.api.user.dto.UpdateUserDto;
import ru.ekbtreeshelp.api.user.mapper.UserMapper;
import ru.ekbtreeshelp.core.entity.User;
import ru.ekbtreeshelp.core.repository.UserRepository;
import ru.ekbtreeshelp.core.utils.CryptoUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private CryptoUtils cryptoUtils;

    @Test
    void getById() {
        userService.getById(1L);

        verify(userRepository, times(1)).getOne(1L);
    }

    @Test
    void update() {
        UpdateUserDto dto = new UpdateUserDto();
        User user = new User();
        user.setId(1L);

        when(userRepository.getOne(1L)).thenReturn(user);

        userService.update(1L, dto);

        verify(userMapper, times(1)).updateUserFromDto(dto, user);
    }

    @Test
    void updatePassword() {
        User user = new User();
        String newPassword = "newPassword";

        when(cryptoUtils.sha256WithSalt(newPassword)).thenReturn(newPassword);

        userService.updatePassword(user, newPassword);

        verify(userRepository, times(1)).save(user);
    }
}