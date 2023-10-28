package com.example.servicesprovider.controller;

import com.example.servicesprovider.model.Client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/client")
public class ClientController {

    @GetMapping("/find/{username}")
    public ResponseEntity<Client> getClient(@PathVariable String username) {
        Client client = Client.builder().firstName("reza").lastName("asghar").userName("tt4wr").
                birthDate(LocalDate.of(2005, 5, 5))
                .password("1wW#qwer").email("qwe@a4sd.com").
                clientCredit(50000D).nationalCode("5555555555").phoneNumber("09999999999").build();
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }
}
