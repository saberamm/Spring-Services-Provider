package com.example.servicesprovider.mapper.impl;

import com.example.servicesprovider.dto.*;
import com.example.servicesprovider.mapper.TechnicianMapper;
import com.example.servicesprovider.model.Technician;
import com.example.servicesprovider.utility.ImageConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TechnicianMapperImpl implements TechnicianMapper {
    ImageConverter imageConverter;

    @Override
    public Technician map(TechnicianRequestDto technicianRequestDto) {
        if (technicianRequestDto == null) return null;
        Technician technician = new Technician();
        technician.setId(technicianRequestDto.getId());
        technician.setFirstName(technicianRequestDto.getFirstName());
        technician.setLastName(technicianRequestDto.getLastName());
        technician.setUserName(technicianRequestDto.getUserName());
        technician.setPassword(technicianRequestDto.getPassword());
        technician.setBirthDate(technicianRequestDto.getBirthDate());
        technician.setEmail(technicianRequestDto.getEmail());
        technician.setTechnicianStatus(technicianRequestDto.getTechnicianStatus());
        technician.setNegativeScore(technicianRequestDto.getNegativeScore());
        technician.setOverallScore(technicianRequestDto.getOverallScore());
        technician.setPhoneNumber(technicianRequestDto.getPhoneNumber());
        technician.setNationalCode(technicianRequestDto.getNationalCode());
        technician.setAboutMe(technicianRequestDto.getAboutMe());
        technician.setTechnicianPhoto(imageConverter.convertMultipartFileToFile(technicianRequestDto.getTechnicianPhoto()));
        return technician;
    }

    @Override
    public void map(TechnicianRequestDto technicianRequestDto, Technician technician) {
        if (technicianRequestDto.getId() != null) {
            technician.setId(technicianRequestDto.getId());
        }
        if (technicianRequestDto.getFirstName() != null) {
            technician.setFirstName(technicianRequestDto.getFirstName());
        }
        if (technicianRequestDto.getLastName() != null) {
            technician.setLastName(technicianRequestDto.getLastName());
        }
        if (technicianRequestDto.getUserName() != null) {
            technician.setUserName(technicianRequestDto.getUserName());
        }
        if (technicianRequestDto.getPassword() != null) {
            technician.setPassword(technicianRequestDto.getPassword());
        }
        if (technicianRequestDto.getBirthDate() != null) {
            technician.setBirthDate(technicianRequestDto.getBirthDate());
        }
        if (technicianRequestDto.getEmail() != null) {
            technician.setEmail(technicianRequestDto.getEmail());
        }
        if (technicianRequestDto.getTechnicianStatus() != null) {
            technician.setTechnicianStatus(technicianRequestDto.getTechnicianStatus());
        }
        if (technicianRequestDto.getNegativeScore() != null) {
            technician.setNegativeScore(technicianRequestDto.getNegativeScore());
        }
        if (technicianRequestDto.getOverallScore() != null) {
            technician.setOverallScore(technicianRequestDto.getOverallScore());
        }
        if (technicianRequestDto.getPhoneNumber() != null) {
            technician.setPhoneNumber(technicianRequestDto.getPhoneNumber());
        }
        if (technicianRequestDto.getNationalCode() != null) {
            technician.setNationalCode(technicianRequestDto.getNationalCode());
        }
        if (technicianRequestDto.getAboutMe() != null) {
            technician.setAboutMe(technicianRequestDto.getAboutMe());
        }
        if (technicianRequestDto.getTechnicianPhoto() != null) {
            technician.setTechnicianPhoto(imageConverter.convertMultipartFileToFile(technicianRequestDto.getTechnicianPhoto()));
        }
    }

}
