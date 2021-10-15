package ru.ekbtreeshelp.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
class User {
    String name;
    String login;
}

@Data
@AllArgsConstructor
class Comment {
    String text;
    Date creationDate;
    User author;
}

@RestController
public class HelloController {
    @GetMapping
    String helloWorld() {
        return "Hello world!";
    }

    @PostMapping
    String helloWorld2(@RequestBody Comment comment) {
        return comment.author.login;
    }

    @GetMapping("/user/{login}")
    User getUserByLogin(@PathVariable String login) {
        return new User(login, login);
    }

    @GetMapping("/comment")
    List<Comment> searchCommentsByText(@RequestParam String text) {
        User user1 = new User("Maksim", "maxim");
        User user2 = new User("Aleksey", "aleksey");

        Comment comment1 = new Comment("abc", new Date(), user1);
        Comment comment2 = new Comment("def", new Date(), user2);

        return Stream.of(comment1, comment2)
                .filter(comment -> comment.text.contains(text))
                .toList();
    }

    @GetMapping("/listComments")
    List<Comment> listComments() {
        User user1 = new User("Maksim", "maxim");
        User user2 = new User("Aleksey", "aleksey");

        Comment comment1 = new Comment("1", new Date(), user1);
        Comment comment2 = new Comment("2", new Date(), user2);

        return List.of(comment1, comment2);
    }
}
