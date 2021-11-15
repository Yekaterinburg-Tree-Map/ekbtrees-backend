package ru.ekbtreeshelp.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    List<UserData> listAllUsers(Integer firstResult, Integer pageSize){
        return userService.getAllUsers(firstResult, pageSize);
    }

    @GetMapping("/user/{id}")
    UserData findUserByID(@PathVariable String id){
        return Users.findUserByID.get(id);
    }
}
