package com.example.servicesprovider.controller;

import com.example.servicesprovider.dto.*;
import com.example.servicesprovider.mapper.OfferMapper;
import com.example.servicesprovider.mapper.OrderMapper;
import com.example.servicesprovider.mapper.TechnicianMapper;
import com.example.servicesprovider.model.Offer;
import com.example.servicesprovider.model.Order;
import com.example.servicesprovider.model.Technician;
import com.example.servicesprovider.model.enumeration.OrderStatus;
import com.example.servicesprovider.service.OrderService;
import com.example.servicesprovider.service.TechnicianService;
import com.example.servicesprovider.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
    OrderService orderService;
    OfferMapper offerMapper;

    @PreAuthorize("hasRole('TECHNICIAN')")
    @GetMapping("/find")
    public ResponseEntity<TechnicianResponseDto> getTechnician() {
        Technician technician = technicianService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        TechnicianResponseDto technicianResponseDto = modelMapper.map(technician, TechnicianResponseDto.class);
        return new ResponseEntity<>(technicianResponseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TECHNICIAN')")
    @PostMapping("/register")
    public ResponseEntity<TechnicianResponseDto> addTechnician(@ModelAttribute @Valid TechnicianRequestDto technicianRequestDto) {
        Technician technician = technicianMapper.map(technicianRequestDto);
        Technician savedTechnician = technicianService.save(technician);
        userService.sendConfirmationEmail(savedTechnician);
        TechnicianResponseDto technicianResponseDto = modelMapper.map(savedTechnician, TechnicianResponseDto.class);
        return new ResponseEntity<>(technicianResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('TECHNICIAN')")
    @DeleteMapping("/delete")
    public void deleteTechnician() {
        technicianService.deleteByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PreAuthorize("hasRole('TECHNICIAN')")
    @PutMapping("/update")
    public ResponseEntity<TechnicianResponseDto> updateTechnician(@ModelAttribute @Valid TechnicianRequestDto technicianRequestDto) {
        if (technicianRequestDto.getPassword() != null) {
            throw new IllegalCallerException("password cant change here please use change password end point");
        }
        Technician technician = technicianService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        technicianMapper.map(technicianRequestDto, technician);
        Technician updatedTechnician = technicianService.update(technician);
        TechnicianResponseDto technicianResponseDto = modelMapper.map(updatedTechnician, TechnicianResponseDto.class);
        return new ResponseEntity<>(technicianResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('TECHNICIAN')")
    @PutMapping("/changePassword")
    public void changePassword(@RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
        userService.changePassword(SecurityContextHolder.getContext().getAuthentication().getName(),
                passwordUpdateRequest.getOldPassword(),
                passwordUpdateRequest.getNewPassword(),
                passwordUpdateRequest.getDuplicateNewPassword());
    }

    @PreAuthorize("hasRole('TECHNICIAN')")
    @GetMapping("/ordersThatTechnicianCanOffer")
    public ResponseEntity<List<OrderResponseDto>> ordersThatTechnicianCanOffer() {
        Technician technician = technicianService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Order> orderList = technicianService.ordersThatTechnicianCanOffer(technician);
        List<OrderResponseDto> orderResponseDtoList = orderList
                .stream()
                .map(order -> orderMapper.map(order))
                .collect(Collectors.toList());

        return new ResponseEntity<>(orderResponseDtoList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TECHNICIAN')")
    @PostMapping("/addOffer")
    public ResponseEntity<OfferResponseDto> addOffer(@RequestBody @Valid OfferRequestDto offerRequestDto) {
        Offer offer = offerMapper.map(offerRequestDto);
        Offer savedOffer = technicianService.addOffer(offer);
        OfferResponseDto offerResponseDto = offerMapper.map(savedOffer);
        return new ResponseEntity<>(offerResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('TECHNICIAN')")
    @GetMapping("/getOverallScore")
    public ResponseEntity<Double> getOverallScore() {
        Double overallScore = technicianService.getOverallScore(SecurityContextHolder.getContext().getAuthentication().getName());
        return new ResponseEntity<>(overallScore, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TECHNICIAN')")
    @GetMapping("/saveTechnicianPhoto")
    public void saveTechnicianPhoto() {
        technicianService.saveTechnicianPhoto(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PreAuthorize("hasRole('TECHNICIAN')")
    @GetMapping("findOrdersByStatus")
    public ResponseEntity<List<OrderResponseDto>> findOrdersByStatus(@RequestParam Long technicianId, @RequestParam OrderStatus orderStatus) {
        List<Order> orders = orderService.findAllByTechnicianIdAndOrderStatus(technicianId, orderStatus);
        List<OrderResponseDto> offerResponseDtoList = orders.stream().map(order -> orderMapper.map(order)).toList();
        return new ResponseEntity<>(offerResponseDtoList, HttpStatus.OK);
    }
}
