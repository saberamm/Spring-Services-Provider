package com.example.servicesprovider.controller;

import com.example.servicesprovider.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    UserService userService;

    @GetMapping("/confirmation")
    public String confirmUser(@RequestParam String token) {
        userService.confirmUser(token);
        return "User confirmed successfully!";
    }
}
