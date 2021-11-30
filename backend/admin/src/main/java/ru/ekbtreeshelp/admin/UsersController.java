package ru.ekbtreeshelp.admin;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@AllArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @GetMapping
    List<UserData> listAllUsers(@RequestParam Integer firstResult, @RequestParam Integer pageSize){
        return usersService.listAllUsers(firstResult, pageSize);
    }

    @GetMapping("/listUsers")
    List<UserData> listWithoutPaging(){
        return usersService.listWithoutPaging();
    }

    @GetMapping("/{id}")
    UserData getByID(@PathVariable Long id){
        return usersService.getByID(id);
    }

    @PutMapping
    UserData update(@RequestBody UserData userData){
        UserData user = usersService.update(userData);
        return user;
    }
}
