package ru.ekbtreeshelp.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Stream;

public class Users {
    static HashMap<Long, UserData> findUserByID;

    void setIDs(List<UserData> userList){
        findUserByID = new HashMap<>();
        for(UserData user: userList){
            findUserByID.put(user.id, user);
        }
    }

    @GetMapping("/listUsers")
    List<UserData> listAllUsers(){
        List<UserData> userList = new ArrayList<>();
        UserData user1 = new UserData("name1", "name1", new Date(), 113133242L, false);
        UserData user2 = new UserData("name2", "name2", new Date(), 323224343L, false);
        userList.add(user1);
        userList.add(user2);
        setIDs(userList);

        return userList;
    }

    @GetMapping("/user/{id}")
    static UserData findUserByID(@PathVariable Long id){
        return findUserByID.get(id);
    }
}