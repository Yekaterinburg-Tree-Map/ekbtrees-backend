package ru.ekbtreeshelp.api.security.permissions.evaluators;

import org.springframework.security.core.Authentication;
import ru.ekbtreeshelp.api.security.JWTUserDetails;

import java.util.Map;
import java.util.function.BiFunction;

public abstract class DomainPermissionEvaluatorBase implements DomainPermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Long targetId, String permission) {
        BiFunction<Authentication, Long, Boolean> permissionChecker = getPermissionCheckers().get(permission);
        if (permissionChecker == null) {
            throw new IllegalArgumentException("Отсутствует проверка для данного permission: " + permission);
        }
        return permissionChecker.apply(authentication, targetId);
    }

    protected boolean userIsAuthor(Authentication authentication, Long entityId) {
        JWTUserDetails principal = (JWTUserDetails) authentication.getPrincipal();
        return getAuthorId(entityId).equals(principal.getId());
    }

    protected abstract Long getAuthorId(Long entityId);

    protected abstract Map<String, BiFunction<Authentication, Long, Boolean>> getPermissionCheckers();
}
