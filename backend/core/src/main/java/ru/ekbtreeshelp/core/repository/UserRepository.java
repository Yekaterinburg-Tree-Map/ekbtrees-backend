package ru.ekbtreeshelp.core.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ekbtreeshelp.core.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    User findByVkId(Long vkId);

    User findByFbId(Long fbId);

    @Query(value = "SELECT * FROM users OFFSET (:pageNumber * :pageSize) LIMIT * :pageSize", nativeQuery = true)
    List<User> listAllUsers(int pageNumber, int pageSize);
}
