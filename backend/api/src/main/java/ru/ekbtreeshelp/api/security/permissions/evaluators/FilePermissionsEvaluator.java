package ru.ekbtreeshelp.api.security.permissions.evaluators;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.ekbtreeshelp.core.repository.FileRepository;
import ru.ekbtreeshelp.api.security.permissions.constants.Domains;
import ru.ekbtreeshelp.api.security.permissions.constants.Permissions;

import java.util.Map;
import java.util.function.BiFunction;

@Component(Domains.FILE + MainPermissionEvaluator.PERMISSION_EVALUATOR)
@RequiredArgsConstructor
public class FilePermissionsEvaluator extends DomainPermissionEvaluatorBase {

    private final FileRepository fileRepository;

    private final Map<String, BiFunction<Authentication, Long, Boolean>> permissionCheckers = Map.of(
            Permissions.DELETE, this::userIsAuthor,
            Permissions.EDIT, this::userIsAuthor
    );

    @Override
    protected Long getAuthorId(Long entityId) {
        return fileRepository.findById(entityId).orElseThrow().getAuthor().getId();
    }

    @Override
    protected Map<String, BiFunction<Authentication, Long, Boolean>> getPermissionCheckers() {
        return permissionCheckers;
    }
}
