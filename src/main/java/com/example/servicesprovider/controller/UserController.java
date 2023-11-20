package com.example.servicesprovider.controller;

import com.example.servicesprovider.dto.ResponseMessage;
import com.example.servicesprovider.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    UserService userService;

    @GetMapping("/confirmation")
    public ResponseEntity<ResponseMessage> confirmUser(@RequestParam String token) {
        userService.confirmUser(token);
        ResponseMessage responseMessage = new ResponseMessage(LocalDateTime.now(), "User confirmed successfully!");
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}
