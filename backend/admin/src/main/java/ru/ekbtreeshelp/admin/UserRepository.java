package ru.ekbtreeshelp.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity findUserEntityByID(Long id);

    @Query("UPDATE UserEntity set blocked = true WHERE id = :id")
    void blocked();

    @Query(nativeQuery = true, value="UPDATE users set is_blocked = true WHERE id = :id")
    void blockUserNative(Long id);

    @Query(value = "SELECT * FROM users OFFSET (:pageNumber * :pageSize) LIMIT * :pageSize", nativeQuery = true)
    List<UserData> findUserEntitiesBySomeValueWithPaging(String value, int pageNumber, int pageSize);
}
