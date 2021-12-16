package ru.ekbtreeshelp.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ekbtreeshelp.api.converter.UserConverter;
import ru.ekbtreeshelp.api.dto.UpdateUserDto;
import ru.ekbtreeshelp.core.entity.User;
import ru.ekbtreeshelp.core.repository.UserRepository;
import ru.ekbtreeshelp.core.utils.CryptoUtils;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final CryptoUtils cryptoUtils;

    public User getById(Long id) {
        return userRepository.getOne(id);
    }

    @Transactional
    public void update(Long id, UpdateUserDto updateUserDto) {
        User user = userRepository.getOne(id);
        userConverter.updateUserFromDto(updateUserDto, user);
    }

    @Transactional
    public void updatePassword(User user, String newPassword) {
        user.setPassword(cryptoUtils.sha256WithSalt(newPassword));
        userRepository.save(user);
    }
}
