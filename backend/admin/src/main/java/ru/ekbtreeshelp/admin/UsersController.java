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

    @GetMapping("/test")
    List<UserData> listAllUsers(){
        List<UserData> userList = new ArrayList<>();
        UserData user1 = new UserData("name1", "name1", new Date(), 113133242L, false);
        UserData user2 = new UserData("name2", "name2", new Date(), 323224343L, false);
        userList.add(user1);
        userList.add(user2);

        return userList;
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
