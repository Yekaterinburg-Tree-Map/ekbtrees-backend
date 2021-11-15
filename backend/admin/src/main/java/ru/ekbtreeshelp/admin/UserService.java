package ru.ekbtreeshelp.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDataRepository repository;

    public List<UserData> getAllUsers(Integer pageNo, Integer pageSize)
    {
        Pageable paging = (Pageable) PageRequest.of(pageNo, pageSize);

        Page<UserData> pagedResult = repository.findAll((org.springframework.data.domain.Pageable) paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<UserData>();
        }
    }

    public void blockUser(Long id){
        UserData userEntity =  Users.findUserByID(id);
        userEntity.setBlocked(true);
        repository.save(userEntity);
    }
}
