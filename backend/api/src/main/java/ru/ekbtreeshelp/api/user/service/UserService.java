package ru.ekbtreeshelp.api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ekbtreeshelp.api.user.dto.UserDto;
import ru.ekbtreeshelp.api.user.mapper.UserMapper;
import ru.ekbtreeshelp.api.user.dto.UpdateUserDto;
import ru.ekbtreeshelp.core.entity.User;
import ru.ekbtreeshelp.core.repository.UserRepository;
import ru.ekbtreeshelp.core.utils.CryptoUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CryptoUtils cryptoUtils;

    public User getById(Long id) {
        return userRepository.getOne(id);
    }

    @Transactional
    public void update(Long id, UpdateUserDto updateUserDto) {
        userMapper.updateUserFromDto(updateUserDto, userRepository.getOne(id));
    }

    @Transactional
    public void updatePassword(User user, String newPassword) {
        user.setPassword(cryptoUtils.sha256WithSalt(newPassword));
        userRepository.save(user);
    }

    public List<UserDto> listUsersWithoutPaging(){
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone()))
                .collect(Collectors.toList());
    }

    public List<UserDto> listAllUsers(int pageNumber, int pageSize){
        return userRepository.listAllUsers(pageNumber, pageSize).stream().map(user -> new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone()))
                .collect(Collectors.toList());
    }

    public UserDto update(UserDto userData){
        User user = userRepository.getOne(userData.getId());
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone());
    }

    public void blockUser(Long id){
        User user = userRepository.getOne(id);
        user.setBlocked(true);
        userRepository.save(user);
    }
}
