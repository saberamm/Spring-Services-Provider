package com.example.servicesprovider.controller;

import com.example.servicesprovider.dto.GeneralServiceRequestDto;
import com.example.servicesprovider.dto.GeneralServiceResponseDto;
import com.example.servicesprovider.model.GeneralService;
import com.example.servicesprovider.service.GeneralService_Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/general-service")
@AllArgsConstructor
public class GeneralServiceController {
    ModelMapper modelMapper;
    GeneralService_Service generalService_service;

    @GetMapping("/find/{serviceName}")
    public ResponseEntity<GeneralServiceResponseDto> getGeneralService(@PathVariable String serviceName) {
        GeneralService generalService = generalService_service.findByServiceName(serviceName);
        GeneralServiceResponseDto generalServiceResponseDto = modelMapper.map(generalService, GeneralServiceResponseDto.class);
        return new ResponseEntity<>(generalServiceResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<GeneralServiceResponseDto> addGeneralService(@RequestBody @Valid GeneralServiceRequestDto generalServiceRequestDto) {
        GeneralService generalService = modelMapper.map(generalServiceRequestDto, GeneralService.class);
        GeneralService savedGeneralService = generalService_service.save(generalService);
        GeneralServiceResponseDto generalServiceResponseDto = modelMapper.map(savedGeneralService, GeneralServiceResponseDto.class);
        return new ResponseEntity<>(generalServiceResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{serviceName}")
    public void deleteGeneralService(@PathVariable String serviceName) {
        generalService_service.deleteByServiceName(serviceName);
    }

    @PutMapping("/update")
    public ResponseEntity<GeneralServiceResponseDto> updateGeneralService(@RequestBody @Valid GeneralServiceRequestDto generalServiceRequestDto) {
        GeneralService generalService = generalService_service.findById(generalServiceRequestDto.getId());
        modelMapper.map(generalServiceRequestDto, generalService);
        GeneralService updatedGeneralService = generalService_service.update(generalService);
        GeneralServiceResponseDto generalServiceResponseDto = modelMapper.map(updatedGeneralService, GeneralServiceResponseDto.class);
        return new ResponseEntity<>(generalServiceResponseDto, HttpStatus.CREATED);
    }
}
