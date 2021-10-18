package ru.ekbtreeshelp.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ekbtreeshelp.api.entity.BaseEntity;
import ru.ekbtreeshelp.api.entity.Tree;
import ru.ekbtreeshelp.api.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TreesController {

    @GetMapping("/trees/byUser")
    List<Tree> allTreesByUser(User user){
        var treesList = Collections.synchronizedList(new ArrayList<Tree>());
        var sortStream = treesList.stream()
                .filter(tree -> tree.getAuthor() == user)
                .sorted(Comparator.comparing(BaseEntity::getCreationDate));
        return sortStream.collect(Collectors.toList());
    }

    @GetMapping("/trees")
    List<Tree> allTreesList(){
        var treesList = Collections.synchronizedList(new ArrayList<Tree>());
        var sortStream = treesList.stream()
                .sorted(Comparator.comparing(BaseEntity::getCreationDate));
        return  sortStream.collect(Collectors.toList());
    }

    @PostMapping("/trees/change")
    void treeChanges(@RequestBody Tree tree, @RequestBody Long id)
    {
        var treesList = Collections.synchronizedList(new ArrayList<Tree>());
        var index = treesList.indexOf(id);
        treesList.set(index,tree);
    }

    @PostMapping("/trees/delete")
    void treeDelete(@RequestBody Long id)
    {
        var treesList = Collections.synchronizedList(new ArrayList<Tree>());
        treesList.remove(id);
    }
}
