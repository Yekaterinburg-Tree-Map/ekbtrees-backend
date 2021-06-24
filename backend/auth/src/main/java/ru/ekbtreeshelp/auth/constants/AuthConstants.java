package ru.ekbtreeshelp.auth.constants;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract class AuthConstants {
    public static final Set<String> DEFAULT_ROLE_NAMES = Set.of(DefaultRoles.VOLUNTEER);
    public static final long REFRESH_TOKEN_MAX_AGE = TimeUnit.DAYS.toSeconds(90);
    public static final long ACCESS_TOKEN_MAX_AGE = TimeUnit.MINUTES.toSeconds(30);
}
