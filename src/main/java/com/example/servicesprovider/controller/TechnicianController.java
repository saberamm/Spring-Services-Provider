package com.example.servicesprovider.controller;

import com.example.servicesprovider.dto.*;
import com.example.servicesprovider.mapper.OfferMapper;
import com.example.servicesprovider.mapper.OrderMapper;
import com.example.servicesprovider.mapper.TechnicianMapper;
import com.example.servicesprovider.model.Offer;
import com.example.servicesprovider.model.Order;
import com.example.servicesprovider.model.Technician;
import com.example.servicesprovider.service.TechnicianService;
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
@RequestMapping("/technician")
@AllArgsConstructor
public class TechnicianController {
    TechnicianService technicianService;
    UserService userService;
    ModelMapper modelMapper;
    TechnicianMapper technicianMapper;
    OrderMapper orderMapper;
    OfferMapper offerMapper;

    @GetMapping("/find/{username}")
    public ResponseEntity<TechnicianResponseDto> getTechnician(@PathVariable String username) {
        Technician technician = technicianService.findByUserName(username);
        TechnicianResponseDto technicianResponseDto = modelMapper.map(technician, TechnicianResponseDto.class);
        return new ResponseEntity<>(technicianResponseDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<TechnicianResponseDto> addTechnician(@ModelAttribute @Valid TechnicianRequestDto technicianRequestDto) {
        Technician technician = technicianMapper.map(technicianRequestDto);
        Technician savedTechnician = technicianService.save(technician);
        TechnicianResponseDto technicianResponseDto = modelMapper.map(savedTechnician, TechnicianResponseDto.class);
        return new ResponseEntity<>(technicianResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{username}")
    public void deleteTechnician(@PathVariable String username) {
        technicianService.deleteByUserName(username);
    }

    @PutMapping("/update")
    public ResponseEntity<TechnicianResponseDto> updateTechnician(@ModelAttribute @Valid TechnicianRequestDto technicianRequestDto) {
        if (technicianRequestDto.getPassword() != null) {
            throw new IllegalCallerException("password cant change here please use change password end point");
        }
        Technician technician = technicianService.findByUserName(technicianRequestDto.getUserName());
        technicianMapper.map(technicianRequestDto, technician);
        Technician updatedTechnician = technicianService.update(technician);
        TechnicianResponseDto technicianResponseDto = modelMapper.map(updatedTechnician, TechnicianResponseDto.class);
        return new ResponseEntity<>(technicianResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/changePassword")
    public void changePassword(@RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
        userService.changePassword(passwordUpdateRequest.getUserName(),
                passwordUpdateRequest.getOldPassword(),
                passwordUpdateRequest.getNewPassword(),
                passwordUpdateRequest.getDuplicateNewPassword());
    }

    @GetMapping("/ordersThatTechnicianCanOffer/{technicianId}")
    public ResponseEntity<List<OrderResponseDto>> ordersThatTechnicianCanOffer(@PathVariable Long technicianId) {
        Technician technician = technicianService.findById(technicianId);
        List<Order> orderList = technicianService.ordersThatTechnicianCanOffer(technician);
        List<OrderResponseDto> orderResponseDtoList = orderList
                .stream()
                .map(order -> orderMapper.map(order))
                .collect(Collectors.toList());

        return new ResponseEntity<>(orderResponseDtoList, HttpStatus.OK);
    }

    @PostMapping("/addOffer")
    public ResponseEntity<OfferResponseDto> addOffer(@RequestBody @Valid OfferRequestDto offerRequestDto) {
        Offer offer = offerMapper.map(offerRequestDto);
        Offer savedOffer = technicianService.addOffer(offer);
        OfferResponseDto offerResponseDto = offerMapper.map(savedOffer);
        return new ResponseEntity<>(offerResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/getOverallScore/{technicianId}")
    public ResponseEntity<Double> getOverallScore(@PathVariable Long technicianId) {
        Double overallScore = technicianService.getOverallScore(technicianId);
        return new ResponseEntity<>(overallScore, HttpStatus.OK);
    }

    @GetMapping("/saveTechnicianPhoto/{technicianId}")
    public void saveTechnicianPhoto(@PathVariable Long technicianId) {
        technicianService.saveTechnicianPhoto(technicianId);
    }
}
