package com.example.servicesprovider.mapper.impl;

import com.example.servicesprovider.dto.*;
import com.example.servicesprovider.mapper.TechnicianMapper;
import com.example.servicesprovider.model.Technician;
import com.example.servicesprovider.utility.ImageConverter;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TechnicianMapperImpl implements TechnicianMapper {
    ImageConverter imageConverter;
    ModelMapper modelMapper;

    @Override
    public Technician map(TechnicianRequestDto technicianRequestDto) {
        if (technicianRequestDto == null) return null;
        Technician technician = modelMapper.map(technicianRequestDto, Technician.class);
        technician.setTechnicianPhoto(imageConverter.convertMultipartFileToFile(technicianRequestDto.getTechnicianPhoto()));
        return technician;
    }

    @Override
    public TechnicianResponseDto map(Technician technician) {
        return null;
    }

    @Override
    public void map(TechnicianRequestDto technicianRequestDto, Technician technician) {
        modelMapper.map(technicianRequestDto, technician);
        if (technicianRequestDto.getTechnicianPhoto() != null) {
            technician.setTechnicianPhoto(imageConverter.convertMultipartFileToFile(technicianRequestDto.getTechnicianPhoto()));
        }
    }

}
