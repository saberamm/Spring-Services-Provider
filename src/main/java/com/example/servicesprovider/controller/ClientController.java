package com.example.servicesprovider.controller;

import com.example.servicesprovider.dto.*;
import com.example.servicesprovider.mapper.OfferMapper;
import com.example.servicesprovider.mapper.OrderMapper;
import com.example.servicesprovider.mapper.SubServiceMapper;
import com.example.servicesprovider.model.*;
import com.example.servicesprovider.service.ClientService;
import com.example.servicesprovider.service.OfferService;
import com.example.servicesprovider.service.OrderService;
import com.example.servicesprovider.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {
    ClientService clientService;
    UserService userService;
    ModelMapper modelMapper;
    SubServiceMapper subServiceMapper;
    OrderMapper orderMapper;
    OfferService offerService;
    OrderService orderService;
    OfferMapper offerMapper;

    @GetMapping("/find/{username}")
    public ResponseEntity<ClientResponseDto> getClient(@PathVariable String username) {
        Client client = clientService.findByUserName(username);
        ClientResponseDto clientResponseDto = modelMapper.map(client, ClientResponseDto.class);
        return new ResponseEntity<>(clientResponseDto, HttpStatus.OK);
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

    @GetMapping("/findGeneralServices")
    public ResponseEntity<List<GeneralServiceResponseDto>> seeGeneralServices() {
        List<GeneralService> generalServiceList = clientService.seeGeneralServices();
        List<GeneralServiceResponseDto> generalServiceResponseDtoList = generalServiceList
                .stream()
                .map(generalService -> modelMapper.map(generalService, GeneralServiceResponseDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(generalServiceResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/findSubServices")
    public ResponseEntity<List<SubServiceResponseDto>> seeSubServices() {
        List<SubService> subServiceList = clientService.seeSubServices();
        List<SubServiceResponseDto> subServiceResponseDtoList = subServiceList
                .stream()
                .map(subService -> subServiceMapper.map(subService))
                .collect(Collectors.toList());

        return new ResponseEntity<>(subServiceResponseDtoList, HttpStatus.OK);
    }

    @PostMapping("/addOrder")
    public ResponseEntity<OrderResponseDto> addOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        Order order = orderMapper.map(orderRequestDto);
        Order savedOrder = clientService.addOrder(order);
        OrderResponseDto orderResponseDto = orderMapper.map(savedOrder);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/getOffersSortedByTechnicianScore/{orderId}")
    public ResponseEntity<List<OfferResponseDto>> getOffersSortedByTechnicianScore(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        List<Offer> offerList = offerService.getOffersSortedByTechnicianScore(order);
        List<OfferResponseDto> offerResponseDtoList = offerList
                .stream()
                .map(offer -> offerMapper.map(offer))
                .collect(Collectors.toList());

        return new ResponseEntity<>(offerResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/getOffersSortedByPrice/{orderId}")
    public ResponseEntity<List<OfferResponseDto>> getOffersSortedByPrice(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        List<Offer> offerList = offerService.getOffersSortedByPrice(order);
        List<OfferResponseDto> offerResponseDtoList = offerList
                .stream()
                .map(offer -> offerMapper.map(offer))
                .collect(Collectors.toList());

        return new ResponseEntity<>(offerResponseDtoList, HttpStatus.OK);
    }

    @PutMapping("/chooseOffer/{offerId}")
    public ResponseEntity<OrderResponseDto> chooseOffer(@PathVariable Long offerId) {
        Offer offer = offerService.findById(offerId);
        Order order = clientService.chooseOffer(offer);
        OrderResponseDto orderResponseDto = orderMapper.map(order);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }
}
