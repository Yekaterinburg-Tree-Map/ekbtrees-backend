package ru.ekbtreeshelp.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ekbtreeshelp.core.entity.User;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Long> {

    @Query("UPDATE UserEntity set blocked = true WHERE id = :id")
    void blockUser();

    @Query(nativeQuery = true, value="UPDATE users set is_blocked = true WHERE id = :id")
    void blockUserNative(Long id);

    @Query(value = "SELECT * FROM users OFFSET (:pageNumber * :pageSize) LIMIT * :pageSize", nativeQuery = true)
    List<UserData> findUserEntitiesBySomeValueWithPaging(String value, int pageNumber, int pageSize);

    @Query(value = "SELECT * FROM users OFFSET (:pageNumber * :pageSize) LIMIT * :pageSize", nativeQuery = true)
    List<UserData> listAllUsers(int pageNumber, int pageSize);

    @Query(value = "SELECT * FROM users", nativeQuery = true)
    List<UserData> listWithoutPaging();


}
