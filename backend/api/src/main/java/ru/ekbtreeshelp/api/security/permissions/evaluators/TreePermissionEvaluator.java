package ru.ekbtreeshelp.api.security.permissions.evaluators;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.ekbtreeshelp.api.repository.TreeRepository;
import ru.ekbtreeshelp.api.security.permissions.constants.Domains;
import ru.ekbtreeshelp.api.security.permissions.constants.Permissions;

import java.util.Map;
import java.util.function.BiFunction;

@Component(Domains.TREE + MainPermissionEvaluator.PERMISSION_EVALUATOR)
@RequiredArgsConstructor
public class TreePermissionEvaluator extends DomainPermissionEvaluatorBase {

    private final TreeRepository treeRepository;

    private final Map<String, BiFunction<Authentication, Long, Boolean>> permissionCheckers = Map.of(
            Permissions.DELETE, this::userIsAuthor
    );

    @Override
    protected Long getAuthorId(Long entityId) {
        return treeRepository.findById(entityId).orElseThrow().getAuthor().getId();
    }

    @Override
    protected Map<String, BiFunction<Authentication, Long, Boolean>> getPermissionCheckers() {
        return permissionCheckers;
    }
}
