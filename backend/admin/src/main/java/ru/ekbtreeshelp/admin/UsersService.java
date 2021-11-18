package ru.ekbtreeshelp.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsersService {

    public final UsersRepository usersRepository;

    List<UserData> listAllUsers(Integer firstResult, Integer pageSize){
        return usersRepository.findAll().stream().map(userEntity -> new UserData(userEntity.getName(), userEntity.getLogin(),
                new Date(), userEntity.getId(), false)).collect(Collectors.toList());
    }

    List<UserData> listWithoutPaging(){
        return usersRepository.findAll().stream().map(userEntity -> new UserData(userEntity.getName(), userEntity.getLogin(),
                new Date(), userEntity.getId(), false)).collect(Collectors.toList());
    }

    public void blockUser(Long id){
        UserEntity user = usersRepository.getOne(id);
        user.setBlocked(true);
        usersRepository.save(user);
    }

    UserData getByID(Long id){
        UserEntity user = usersRepository.getOne(id);
        return new UserData(user.getName(), user.getLogin(), user.getRegistryDate(), user.getId(), user.isBlocked());
    }

    UserData update(UserData userData){
        UserEntity user = usersRepository.getOne(userData.id);
        return new UserData(user.getName(), user.getLogin(), user.getRegistryDate(), user.getId(), user.isBlocked());
    }
}
