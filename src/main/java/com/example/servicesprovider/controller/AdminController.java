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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/{username}")
    public ResponseEntity<AdminResponseDto> getAdmin(@PathVariable String username) {
        Admin admin = adminService.findByUserName(username);
        AdminResponseDto adminResponseDto = modelMapper.map(admin, AdminResponseDto.class);
        return new ResponseEntity<>(adminResponseDto, HttpStatus.OK);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<AdminResponseDto> addAdmin(@RequestBody @Valid AdminRequestDto adminRequestDto) {
        Admin admin = modelMapper.map(adminRequestDto, Admin.class);
        Admin savedAdmin = adminService.save(admin);
        userService.sendConfirmationEmail(savedAdmin);
        AdminResponseDto adminResponseDto = modelMapper.map(savedAdmin, AdminResponseDto.class);
        return new ResponseEntity<>(adminResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<ResponseMessage> deleteAdmin(@PathVariable String username) {
        adminService.deleteByUserName(username);
        ResponseMessage responseMessage = new ResponseMessage(LocalDateTime.now(), "Admin deleted successfully");
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/changePassword")
    public ResponseEntity<ResponseMessage> changePassword(@RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
        userService.changePassword(SecurityContextHolder.getContext().getAuthentication().getName(),
                passwordUpdateRequest.getOldPassword(),
                passwordUpdateRequest.getNewPassword(),
                passwordUpdateRequest.getDuplicateNewPassword());
        ResponseMessage responseMessage = new ResponseMessage(LocalDateTime.now(), "password changed successfully");
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addGeneralService")
    public ResponseEntity<GeneralServiceResponseDto> addGeneralService(@RequestBody @Valid GeneralServiceRequestDto generalServiceRequestDto) {
        GeneralService generalService = modelMapper.map(generalServiceRequestDto, GeneralService.class);
        GeneralService savedGeneralService = adminService.addGeneralService(generalService);
        GeneralServiceResponseDto generalServiceResponseDto = modelMapper.map(savedGeneralService, GeneralServiceResponseDto.class);
        return new ResponseEntity<>(generalServiceResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addSubService")
    public ResponseEntity<SubServiceResponseDto> addSubService(@RequestBody @Valid SubServiceRequestDto subServiceRequestDto) {
        SubService subService = subServiceMapper.map(subServiceRequestDto);
        SubService savedSubService = adminService.addSubService(subService);
        SubServiceResponseDto subServiceResponseDto = subServiceMapper.map(savedSubService);
        return new ResponseEntity<>(subServiceResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addTechnician")
    public ResponseEntity<TechnicianResponseDto> addTechnician(@ModelAttribute @Valid TechnicianRequestDto technicianRequestDto) {
        Technician technician = technicianMapper.map(technicianRequestDto);
        Technician savedTechnician = adminService.addTechnician(technician);
        userService.sendConfirmationEmail(savedTechnician);
        TechnicianResponseDto technicianResponseDto = modelMapper.map(savedTechnician, TechnicianResponseDto.class);
        return new ResponseEntity<>(technicianResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/addTechnicianToSubService")
    public ResponseEntity<ResponseMessage> addTechnicianToSubService(@RequestParam Long technicianId, @RequestParam Long subServiceId) {
        Technician technician = technicianService.findById(technicianId);
        SubService subService = subService_service.findById(subServiceId);
        adminService.addTechnicianToSubService(technician, subService);
        ResponseMessage responseMessage = new ResponseMessage(LocalDateTime.now(), "Technician added to sub service successfully");
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteTechnicianFromSubService")
    public ResponseEntity<ResponseMessage> deleteTechnicianFromSubService(@RequestParam Long technicianId, @RequestParam Long subServiceId) {
        Technician technician = technicianService.findById(technicianId);
        SubService subService = subService_service.findById(subServiceId);
        adminService.deleteTechnicianFromSubService(technician, subService);
        ResponseMessage responseMessage = new ResponseMessage(LocalDateTime.now(), "Technician deleted from sub service successfully");
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/confirmTechnician/{technicianId}")
    public ResponseEntity<TechnicianResponseDto> confirmTechnician(@PathVariable Long technicianId) {
        Technician technician = technicianService.findById(technicianId);
        Technician confirmTechnician = adminService.confirmTechnician(technician);
        TechnicianResponseDto technicianResponseDto = modelMapper.map(confirmTechnician, TechnicianResponseDto.class);
        return new ResponseEntity<>(technicianResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateSubServicePrice")
    public ResponseEntity<SubServiceResponseDto> updateSubServicePrice(@RequestParam Long subServiceId, @RequestParam Double basePrice) {
        SubService updatedSubService = adminService.updateSubServicePrice(subServiceId, basePrice);
        SubServiceResponseDto subServiceResponseDto = subServiceMapper.map(updatedSubService);
        return new ResponseEntity<>(subServiceResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateSubServiceName")
    public ResponseEntity<SubServiceResponseDto> updateSubServiceName(@RequestParam Long subServiceId, @RequestParam String subServiceName) {
        SubService updatedSubService = adminService.updateSubServiceName(subServiceId, subServiceName);
        SubServiceResponseDto subServiceResponseDto = subServiceMapper.map(updatedSubService);
        return new ResponseEntity<>(subServiceResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
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
