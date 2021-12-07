package ru.ekbtreeshelp.api.security.permissions.evaluators;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.ekbtreeshelp.api.security.permissions.constants.Domains;
import ru.ekbtreeshelp.core.repository.CommentRepository;
import ru.ekbtreeshelp.api.security.permissions.constants.Permissions;

import java.util.Map;
import java.util.function.BiFunction;

@Component(Domains.COMMENT + MainPermissionEvaluator.PERMISSION_EVALUATOR)
@RequiredArgsConstructor
public class CommentPermissionEvaluator extends DomainPermissionEvaluatorBase {

    private final CommentRepository commentRepository;

    private final Map<String, BiFunction<Authentication, Long, Boolean>> permissionCheckers = Map.of(
            Permissions.DELETE, this::userIsAuthor,
            Permissions.EDIT, this::userIsAuthor
    );

    @Override
    protected Long getAuthorId(Long entityId) {
        return commentRepository.findById(entityId).orElseThrow().getAuthor().getId();
    }

    @Override
    protected Map<String, BiFunction<Authentication, Long, Boolean>> getPermissionCheckers() {
        return permissionCheckers;
    }
}
