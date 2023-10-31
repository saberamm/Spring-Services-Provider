package com.example.servicesprovider.mapper.impl;

import com.example.servicesprovider.dto.GeneralServiceResponseDto;
import com.example.servicesprovider.dto.SubServiceRequestDto;
import com.example.servicesprovider.dto.SubServiceResponseDto;
import com.example.servicesprovider.mapper.SubServiceMapper;
import com.example.servicesprovider.model.SubService;
import com.example.servicesprovider.service.GeneralService_Service;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SubServiceMapperImpl implements SubServiceMapper {

    private final ModelMapper modelMapper;
    GeneralService_Service generalService_service;

    @Override
    public SubService map(SubServiceRequestDto subServiceRequestDto) {
        if (subServiceRequestDto == null) return null;
        SubService subService = modelMapper.map(subServiceRequestDto, SubService.class);
        subService.setGeneralService(generalService_service.findById(subServiceRequestDto.getGeneralServiceId()));
        return subService;
    }

    @Override
    public SubServiceResponseDto map(SubService subService) {
        if (subService == null) return null;
        SubServiceResponseDto subServiceResponseDto = modelMapper.map(subService, SubServiceResponseDto.class);
        subServiceResponseDto.setGeneralServiceResponseDto(modelMapper.map(subService.getGeneralService(), GeneralServiceResponseDto.class));
        return subServiceResponseDto;
    }

    @Override
    public void map(SubServiceRequestDto subServiceRequestDto, SubService subService) {
        modelMapper.map(subServiceRequestDto, SubService.class);

        if (subServiceRequestDto.getGeneralServiceId() != null) {
            subService.setGeneralService(generalService_service.findById(subServiceRequestDto.getGeneralServiceId()));
        }
    }
}
