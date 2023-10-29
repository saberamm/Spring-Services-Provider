package com.example.servicesprovider.controller;

import com.example.servicesprovider.dto.*;
import com.example.servicesprovider.model.Admin;
import com.example.servicesprovider.service.AdminService;
import com.example.servicesprovider.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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


    @GetMapping("/find/{username}")
    public ResponseEntity<AdminResponseDto> getAdmin(@PathVariable String username) {
        Admin admin = adminService.findByUserName(username);
        AdminResponseDto adminResponseDto = modelMapper.map(admin, AdminResponseDto.class);
        return new ResponseEntity<>(adminResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<AdminResponseDto> addAdmin(@RequestBody @Valid AdminRequestDto adminRequestDto) {
        Admin admin = modelMapper.map(adminRequestDto, Admin.class);
        Admin savedAdmin = adminService.save(admin);
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

    @PutMapping("/change-password")
    public void changePassword(@RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
        userService.changePassword(passwordUpdateRequest.getUserName(),
                passwordUpdateRequest.getOldPassword(),
                passwordUpdateRequest.getNewPassword(),
                passwordUpdateRequest.getDuplicateNewPassword());
    }
}
