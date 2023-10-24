package com.example.servicesprovider.service;

import com.example.servicesprovider.model.SubServiceTechnician;
import org.springframework.stereotype.Service;

@Service
public interface SubServiceTechnicianService {

    SubServiceTechnician save(SubServiceTechnician subServiceTechnician);

    SubServiceTechnician update(SubServiceTechnician subServiceTechnician);

    void delete(SubServiceTechnician subServiceTechnician);
}
