package com.example.servicesprovider.controller;

import com.example.servicesprovider.dto.*;
import com.example.servicesprovider.mapper.OrderMapper;
import com.example.servicesprovider.mapper.SubServiceMapper;
import com.example.servicesprovider.mapper.TechnicianMapper;
import com.example.servicesprovider.model.*;
import com.example.servicesprovider.service.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    AdminService adminService;
    UserService userService;
    ModelMapper modelMapper;
    SubServiceMapper subServiceMapper;
    TechnicianMapper technicianMapper;
    TechnicianService technicianService;
    SubService_Service subService_service;
    OrderService orderService;
    OrderMapper orderMapper;


    @GetMapping("/find/{username}")
    public ResponseEntity<AdminResponseDto> getAdmin(@PathVariable String username) {
        Admin admin = adminService.findByUserName(username);
        AdminResponseDto adminResponseDto = modelMapper.map(admin, AdminResponseDto.class);
        return new ResponseEntity<>(adminResponseDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AdminResponseDto> addAdmin(@RequestBody @Valid AdminRequestDto adminRequestDto) {
        Admin admin = modelMapper.map(adminRequestDto, Admin.class);
        Admin savedAdmin = adminService.save(admin);
        userService.sendConfirmationEmail(savedAdmin);
        AdminResponseDto adminResponseDto = modelMapper.map(savedAdmin, AdminResponseDto.class);
        return new ResponseEntity<>(adminResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{username}")
    public void deleteAdmin(@PathVariable String username) {
        adminService.deleteByUserName(username);
    }

    @PutMapping("/update")
    public ResponseEntity<AdminResponseDto> updateAdmin(@RequestBody @Valid AdminRequestDto adminRequestDto) {
        if (adminRequestDto.getPassword() != null) {
            throw new IllegalCallerException("password cant change here please use change password end point");
        }
        Admin admin = adminService.findByUserName(adminRequestDto.getUserName());
        modelMapper.map(adminRequestDto, admin);
        Admin updatedAdmin = adminService.update(admin);
        AdminResponseDto adminResponseDto = modelMapper.map(updatedAdmin, AdminResponseDto.class);
        return new ResponseEntity<>(adminResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/changePassword")
    public void changePassword(@RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
        userService.changePassword(passwordUpdateRequest.getUserName(),
                passwordUpdateRequest.getOldPassword(),
                passwordUpdateRequest.getNewPassword(),
                passwordUpdateRequest.getDuplicateNewPassword());
    }

    @PostMapping("/addGeneralService")
    public ResponseEntity<GeneralServiceResponseDto> addGeneralService(@RequestBody @Valid GeneralServiceRequestDto generalServiceRequestDto) {
        GeneralService generalService = modelMapper.map(generalServiceRequestDto, GeneralService.class);
        GeneralService savedGeneralService = adminService.addGeneralService(generalService);
        GeneralServiceResponseDto generalServiceResponseDto = modelMapper.map(savedGeneralService, GeneralServiceResponseDto.class);
        return new ResponseEntity<>(generalServiceResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/addSubService")
    public ResponseEntity<SubServiceResponseDto> addSubService(@RequestBody @Valid SubServiceRequestDto subServiceRequestDto) {
        SubService subService = subServiceMapper.map(subServiceRequestDto);
        SubService savedSubService = adminService.addSubService(subService);
        SubServiceResponseDto subServiceResponseDto = subServiceMapper.map(savedSubService);
        return new ResponseEntity<>(subServiceResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/addTechnician")
    public ResponseEntity<TechnicianResponseDto> addTechnician(@ModelAttribute @Valid TechnicianRequestDto technicianRequestDto) {
        Technician technician = technicianMapper.map(technicianRequestDto);
        Technician savedTechnician = adminService.addTechnician(technician);
        TechnicianResponseDto technicianResponseDto = modelMapper.map(savedTechnician, TechnicianResponseDto.class);
        return new ResponseEntity<>(technicianResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/addTechnicianToSubService/{technicianId}/{subServiceId}")
    public void addTechnicianToSubService(@PathVariable Long technicianId, @PathVariable Long subServiceId) {
        Technician technician = technicianService.findById(technicianId);
        SubService subService = subService_service.findById(subServiceId);
        adminService.addTechnicianToSubService(technician, subService);
    }

    @DeleteMapping("/deleteTechnicianFromSubService/{technicianId}/{subServiceId}")
    public void deleteTechnicianFromSubService(@PathVariable Long technicianId, @PathVariable Long subServiceId) {
        Technician technician = technicianService.findById(technicianId);
        SubService subService = subService_service.findById(subServiceId);
        adminService.deleteTechnicianFromSubService(technician, subService);
    }

    @PutMapping("/confirmTechnician/{technicianId}")
    public ResponseEntity<TechnicianResponseDto> confirmTechnician(@PathVariable Long technicianId) {
        Technician technician = technicianService.findById(technicianId);
        Technician confirmTechnician = adminService.confirmTechnician(technician);
        TechnicianResponseDto technicianResponseDto = modelMapper.map(confirmTechnician, TechnicianResponseDto.class);
        return new ResponseEntity<>(technicianResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/updateSubServicePrice/{subServiceId}/{basePrice}")
    public ResponseEntity<SubServiceResponseDto> updateSubServicePrice(@PathVariable Long subServiceId, @PathVariable Double basePrice) {
        SubService updatedSubService = adminService.updateSubServicePrice(subServiceId, basePrice);
        SubServiceResponseDto subServiceResponseDto = subServiceMapper.map(updatedSubService);
        return new ResponseEntity<>(subServiceResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/updateSubServiceName/{subServiceId}/{subServiceName}")
    public ResponseEntity<SubServiceResponseDto> updateSubServiceName(@PathVariable Long subServiceId, @PathVariable String subServiceName) {
        SubService updatedSubService = adminService.updateSubServiceName(subServiceId, subServiceName);
        SubServiceResponseDto subServiceResponseDto = subServiceMapper.map(updatedSubService);
        return new ResponseEntity<>(subServiceResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/userFilter")
    public ResponseEntity<Page<UserFilterResponseDto>> searchAndFilterUsers(
            @RequestBody UserFilterRequestDto userFilterRequestDto,
            @RequestParam(required = false) Sort.Direction direction,
            @RequestParam(required = false) int pageNumber,
            @RequestParam(required = false) int pageSize,
            @RequestParam(defaultValue = "firstName") String sortBy
    ) {
        Page<User> users = userService.searchAndFilterUsers(userFilterRequestDto, direction, pageNumber, pageSize, sortBy);
        Page<UserFilterResponseDto> usersDto = users.map(user -> modelMapper.map(user, UserFilterResponseDto.class));
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @GetMapping("/orderFilter")
    public ResponseEntity<Page<OrderResponseDto>> searchAndFilterOrders(
            @RequestBody OrderFilterRequestDto orderFilterRequestDto,
            @RequestParam(required = false) Sort.Direction direction,
            @RequestParam(required = false) int pageNumber,
            @RequestParam(required = false) int pageSize,
            @RequestParam(defaultValue = "orderStatus") String sortBy
    ) {
        Page<Order> orders = orderService.searchAndFilterOrders(orderFilterRequestDto, direction, pageNumber, pageSize, sortBy);
        Page<OrderResponseDto> ordersDto = orders.map(order -> orderMapper.map(order));
        return new ResponseEntity<>(ordersDto, HttpStatus.OK);
    }
}
