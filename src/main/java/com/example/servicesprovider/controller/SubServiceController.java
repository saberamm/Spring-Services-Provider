package com.example.servicesprovider.controller;

import com.example.servicesprovider.dto.SubServiceRequestDto;
import com.example.servicesprovider.dto.SubServiceResponseDto;
import com.example.servicesprovider.mapper.SubServiceMapper;
import com.example.servicesprovider.model.SubService;
import com.example.servicesprovider.service.SubService_Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subService")
@AllArgsConstructor
public class SubServiceController {
    SubService_Service subService_service;
    SubServiceMapper subServiceMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/{subServiceId}")
    public ResponseEntity<SubServiceResponseDto> getSubService(@PathVariable Long subServiceId) {
        SubService subService = subService_service.findById(subServiceId);
        SubServiceResponseDto subServiceResponseDto = subServiceMapper.map(subService);
        return new ResponseEntity<>(subServiceResponseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<SubServiceResponseDto> addSubService(@RequestBody @Valid SubServiceRequestDto subServiceRequestDto) {
        SubService subService = subServiceMapper.map(subServiceRequestDto);
        SubService savedSubService = subService_service.save(subService);
        SubServiceResponseDto subServiceResponseDto = subServiceMapper.map(savedSubService);
        return new ResponseEntity<>(subServiceResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{subServiceId}")
    public void deleteSubService(@PathVariable Long subServiceId) {
        subService_service.deleteById(subServiceId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<SubServiceResponseDto> updateSubService(@RequestBody @Valid SubServiceRequestDto subServiceRequestDto) {
        SubService subService = subService_service.findById(subServiceRequestDto.getId());
        subServiceMapper.map(subServiceRequestDto, subService);
        SubService updatedSubService = subService_service.update(subService);
        SubServiceResponseDto subServiceResponseDto = subServiceMapper.map(updatedSubService);
        return new ResponseEntity<>(subServiceResponseDto, HttpStatus.CREATED);
    }
}
