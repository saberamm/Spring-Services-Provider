package com.example.servicesprovider.controller;

import com.example.servicesprovider.dto.ClientRequestDto;
import com.example.servicesprovider.dto.ClientResponseDto;
import com.example.servicesprovider.dto.PasswordUpdateRequest;
import com.example.servicesprovider.model.Client;
import com.example.servicesprovider.service.ClientService;
import com.example.servicesprovider.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {
    ClientService clientService;
    UserService userService;
    ModelMapper modelMapper;

    @GetMapping("/find/{username}")
    public ResponseEntity<ClientResponseDto> getClient(@PathVariable String username) {
        Client client = clientService.findByUserName(username);
        ClientResponseDto clientResponseDto = modelMapper.map(client, ClientResponseDto.class);
        return new ResponseEntity<>(clientResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<ClientResponseDto> addClient(@RequestBody @Valid ClientRequestDto clientRequestDto) {
        Client client = modelMapper.map(clientRequestDto, Client.class);
        Client savedClient = clientService.save(client);
        ClientResponseDto clientResponseDto = modelMapper.map(savedClient, ClientResponseDto.class);
        return new ResponseEntity<>(clientResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{username}")
    public void deleteClient(@PathVariable String username) {
        clientService.deleteByUserName(username);
    }

    @PutMapping("/update")
    public ResponseEntity<ClientResponseDto> updateClient(@RequestBody @Valid ClientRequestDto clientRequestDto) {
        if (clientRequestDto.getPassword() != null) {
            throw new IllegalCallerException("password cant change here please use change password end point");
        }
        Client client = clientService.findByUserName(clientRequestDto.getUserName());
        modelMapper.map(clientRequestDto, client);
        Client updatedClient = clientService.update(client);
        ClientResponseDto clientResponseDto = modelMapper.map(updatedClient, ClientResponseDto.class);
        return new ResponseEntity<>(clientResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/changePassword")
    public void changePassword(@RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
        userService.changePassword(passwordUpdateRequest.getUserName(),
                passwordUpdateRequest.getOldPassword(),
                passwordUpdateRequest.getNewPassword(),
                passwordUpdateRequest.getDuplicateNewPassword());
    }

    @GetMapping("/findGeneralServices/")
    public ResponseEntity<ClientResponseDto> seeGeneralServices() {
    }
}
