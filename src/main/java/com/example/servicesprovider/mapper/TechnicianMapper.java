package com.example.servicesprovider.mapper;

import com.example.servicesprovider.dto.TechnicianRequestDto;
import com.example.servicesprovider.model.Technician;

public interface TechnicianMapper {
    Technician map(TechnicianRequestDto technicianRequestDto);

    void map(TechnicianRequestDto technicianRequestDto, Technician technician);
}
