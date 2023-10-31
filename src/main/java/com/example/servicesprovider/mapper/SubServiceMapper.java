package com.example.servicesprovider.mapper;

import com.example.servicesprovider.dto.SubServiceRequestDto;
import com.example.servicesprovider.dto.SubServiceResponseDto;
import com.example.servicesprovider.model.SubService;

public interface SubServiceMapper {
    SubService requestToSubService(SubServiceRequestDto subServiceRequestDto);

    SubServiceResponseDto subServiceToResponse(SubService subService);
}
