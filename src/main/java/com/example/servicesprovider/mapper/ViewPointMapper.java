package com.example.servicesprovider.mapper;

import com.example.servicesprovider.dto.ViewPointRequestDto;
import com.example.servicesprovider.dto.ViewPointResponseDto;
import com.example.servicesprovider.model.ViewPoint;

public interface ViewPointMapper {
    ViewPoint requestToViewPoint(ViewPointRequestDto viewPointRequestDto);

    ViewPointResponseDto viewPointToResponse(ViewPoint viewPoint);
}
