package com.example.servicesprovider.mapper;

import com.example.servicesprovider.dto.ViewPointRequestDto;
import com.example.servicesprovider.dto.ViewPointResponseDto;
import com.example.servicesprovider.model.ViewPoint;

public interface ViewPointMapper {
    ViewPoint map(ViewPointRequestDto viewPointRequestDto);

    ViewPointResponseDto map(ViewPoint viewPoint);

    void map(ViewPointRequestDto viewPointRequestDto, ViewPoint viewPoint);
}
