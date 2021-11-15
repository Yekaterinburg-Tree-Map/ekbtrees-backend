package ru.ekbtreeshelp.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ekbtreeshelp.api.dto.UserDto;
import ru.ekbtreeshelp.core.entity.User;
import ru.ekbtreeshelp.core.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow();
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