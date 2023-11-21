package com.example.servicesprovider.service;

import com.example.servicesprovider.model.SubServiceTechnician;

public interface SubServiceTechnicianService {

    SubServiceTechnician save(SubServiceTechnician subServiceTechnician);

    SubServiceTechnician update(SubServiceTechnician subServiceTechnician);

    void delete(SubServiceTechnician subServiceTechnician);

    void deleteByTechnicianAndSubService(Long technicianId, Long subServiceId);
}
