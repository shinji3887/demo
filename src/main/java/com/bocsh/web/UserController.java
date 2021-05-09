package com.bocsh.web;

import com.bocsh.domain.User;
import com.bocsh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Value("${book.bookName}")
    private String bookName;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id) {

        if(id==3333){
            System.out.println("it's me!");
        }

        if (id==1111){
            return "myUser";
        }
        else{
            return userService.getTellerName(id);
        }
    }

    @GetMapping("/list")
    public List getUserList() {

        List list = new ArrayList<>();

        User alice = new User();
        alice.setAge(25);
        alice.setName("Alice");
        alice.setSchool("oxford");

        list.add(alice);

        User bob = new User();
        bob.setAge(28);
        bob.setName("Bob");
        bob.setSchool("yale");

        list.add(bob);

        return list;

    }

    @GetMapping("/prop")
    public String getProp() {

        return bookName;
    }
}
