package ru.naumen.ectmauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.ectmauth.entity.User;
import ru.naumen.ectmauth.repository.UserRepository;
import ru.naumen.ectmauth.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User findByVk_id(String vk_id){
        return userRepository.findByVk_id(vk_id);
    }
    public User findByFb_id(String fb_id){
        return userRepository.findByFb_id(fb_id);
    }
    public Optional<User> findById(Long id){return userRepository.findById(id);}
}