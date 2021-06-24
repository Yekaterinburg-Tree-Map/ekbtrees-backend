package ru.ekbtreeshelp.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ekbtreeshelp.auth.entity.Role;

import java.util.Collection;
import java.util.Set;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByName(String name);

    @Query("from Role where name in (:names)")
    Set<Role> findAllByNames(Collection<String> names);

}
