package com.example.servicesprovider.utility;

import com.example.servicesprovider.model.User;
import com.example.servicesprovider.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Main implements CommandLineRunner {
    UserService userService;

    public Main(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        User user = new User("aa", "aa",
                "aa", "yyOs1#222",
                LocalDate.of(2000, 2, 2), "sss@sss.com");

        userService.save(user);
    }
}