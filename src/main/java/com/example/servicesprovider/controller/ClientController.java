package com.example.servicesprovider.controller;

import com.example.servicesprovider.dto.ClientRequestDto;
import com.example.servicesprovider.dto.ClientResponseDto;
import com.example.servicesprovider.model.Client;
import com.example.servicesprovider.service.ClientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {
    ClientService clientService;
    ModelMapper modelMapper;

    @GetMapping("/find/{username}")
    public ResponseEntity<Client> getClient(@PathVariable String username) {
        Client client = Client.builder().firstName("reza").lastName("asghar").userName("tt4wr").
                birthDate(LocalDate.of(2005, 5, 5))
                .password("1wW#qwer").email("qwe@a4sd.com").
                clientCredit(50000D).nationalCode("5555555555").phoneNumber("09999999999").build();
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<ClientResponseDto> addClient(@RequestBody @Valid ClientRequestDto clientRequestDto) {
        Client client = modelMapper.map(clientRequestDto, Client.class);
        Client savedClient = clientService.save(client);
        ClientResponseDto clientResponseDto = modelMapper.map(savedClient, ClientResponseDto.class);
        return new ResponseEntity<>(clientResponseDto, HttpStatus.CREATED);
    }
}
