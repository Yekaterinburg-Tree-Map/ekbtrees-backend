package ru.ekbtreeshelp.api.security.permissions.constants;

import org.springframework.stereotype.Component;

@Component("Roles")
public final class Roles {
    public static final String SUPERUSER = "superuser";
    public static final String MODERATOR = "moderator";
    public static final String VOLUNTEER = "volunteer";
}
