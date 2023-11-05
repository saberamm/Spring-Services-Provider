package com.example.servicesprovider.mapper;

import com.example.servicesprovider.dto.TechnicianRequestDto;
import com.example.servicesprovider.dto.TechnicianResponseDto;
import com.example.servicesprovider.model.Technician;

public interface TechnicianMapper {
    Technician map(TechnicianRequestDto technicianRequestDto);

    TechnicianResponseDto map(Technician technician);

    void map(TechnicianRequestDto technicianRequestDto, Technician technician);
}
