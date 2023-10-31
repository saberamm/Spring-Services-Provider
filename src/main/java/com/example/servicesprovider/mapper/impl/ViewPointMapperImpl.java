package com.example.servicesprovider.mapper.impl;

import com.example.servicesprovider.dto.TechnicianResponseDto;
import com.example.servicesprovider.dto.ViewPointRequestDto;
import com.example.servicesprovider.dto.ViewPointResponseDto;
import com.example.servicesprovider.mapper.ViewPointMapper;
import com.example.servicesprovider.model.ViewPoint;
import com.example.servicesprovider.service.TechnicianService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ViewPointMapperImpl implements ViewPointMapper {

    private final ModelMapper modelMapper;
    TechnicianService technicianService;

    @Override
    public ViewPoint map(ViewPointRequestDto viewPointRequestDto) {
        if (viewPointRequestDto == null) return null;
        ViewPoint viewPoint = modelMapper.map(viewPointRequestDto, ViewPoint.class);
        viewPoint.setTechnician(technicianService.findById(viewPointRequestDto.getTechnicianId()));
        return viewPoint;
    }

    @Override
    public ViewPointResponseDto map(ViewPoint viewPoint) {
        if (viewPoint == null) return null;
        ViewPointResponseDto viewPointResponseDto = modelMapper.map(viewPoint, ViewPointResponseDto.class);
        viewPointResponseDto.setTechnicianResponseDto(modelMapper.map(viewPoint.getTechnician(), TechnicianResponseDto.class));
        return viewPointResponseDto;
    }

    @Override
    public void map(ViewPointRequestDto viewPointRequestDto, ViewPoint viewPoint) {
        modelMapper.map(viewPointRequestDto, ViewPoint.class);

        if (viewPointRequestDto.getTechnicianId() != null) {
            viewPoint.setTechnician(technicianService.findById(viewPointRequestDto.getTechnicianId()));
        }
    }
}
