package ru.ekbtreeshelp.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ekbtreeshelp.auth.constants.AuthConstants;
import ru.ekbtreeshelp.auth.dto.NewUserDto;
import ru.ekbtreeshelp.auth.exception.AuthServiceException;
import ru.ekbtreeshelp.core.utils.CryptoUtils;
import ru.ekbtreeshelp.core.entity.Provider;
import ru.ekbtreeshelp.core.entity.User;
import ru.ekbtreeshelp.core.repository.RoleRepository;
import ru.ekbtreeshelp.core.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CryptoUtils cryptoUtils;

    public User registerNewUser(NewUserDto newUserDto) {

        String email = newUserDto.getEmail();

        if (email == null || newUserDto.getPassword() == null) {
            throw new AuthServiceException("Email and password are required");
        }

        if (isUserExists(email)) {
            throw new AuthServiceException("Email is already in use");
        }

        User user = new User();
        user.setFirstName(newUserDto.getFirstName());
        user.setLastName(newUserDto.getLastName());
        user.setEmail(email);
        user.setEnabled(true);
        user.setPassword(cryptoUtils.sha256WithSalt(newUserDto.getPassword()));
        user.setRoles(roleRepository.findAllByNames(AuthConstants.DEFAULT_ROLE_NAMES));
        user.setProvider(Provider.local);

        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public boolean isUserExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
