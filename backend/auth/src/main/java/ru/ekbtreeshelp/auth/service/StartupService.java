package ru.ekbtreeshelp.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ekbtreeshelp.auth.constants.DefaultRoles;
import ru.ekbtreeshelp.auth.dto.NewUserDto;
import ru.ekbtreeshelp.core.entity.Role;
import ru.ekbtreeshelp.core.entity.User;
import ru.ekbtreeshelp.core.repository.RoleRepository;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StartupService {

    private final RoleRepository roleRepository;
    private final UserService userService;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createDefaultRoles();
        createSuperUser();
    }

    protected void createDefaultRoles() {
        Set.of("superuser", "moderator", "volunteer").forEach(roleName -> {
            if (!roleRepository.existsByName(roleName)) {
                roleRepository.save(new Role(roleName));
            }
        });
    }

    @Transactional
    protected void createSuperUser() {
        String superUserEmail = "superuser@ekbtreeshelp.ru";

        if (!userService.isUserExists(superUserEmail)) {
            User superUser = userService.registerNewUser(
                    new NewUserDto("super",
                            "user",
                            "superuser@ekbtreeshelp.ru",
                            "ItIsChangedInProduction"
                    )
            );

            superUser.setRoles(roleRepository.findAllByNames(List.of(DefaultRoles.SUPERUSER)));
            userService.update(superUser);
        }
    }
}
