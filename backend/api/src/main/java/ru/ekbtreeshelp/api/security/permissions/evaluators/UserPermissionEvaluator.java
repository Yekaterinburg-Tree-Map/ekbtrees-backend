package ru.ekbtreeshelp.api.security.permissions.evaluators;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.ekbtreeshelp.api.security.permissions.constants.Domains;
import ru.ekbtreeshelp.api.security.permissions.constants.Permissions;

import java.util.Map;
import java.util.function.BiFunction;

@Component(Domains.USER + MainPermissionEvaluator.PERMISSION_EVALUATOR)
@RequiredArgsConstructor
public class UserPermissionEvaluator extends DomainPermissionEvaluatorBase {

    private final Map<String, BiFunction<Authentication, Long, Boolean>> permissionCheckers = Map.of(
            Permissions.EDIT, this::userIsAuthor,
            Permissions.DELETE, this::userIsAuthor
    );

    @Override
    protected Long getAuthorId(Long entityId) {
        return entityId;
    }

    @Override
    protected Map<String, BiFunction<Authentication, Long, Boolean>> getPermissionCheckers() {
        return permissionCheckers;
    }
}
