package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.model.SubServiceTechnician;
import com.example.servicesprovider.repository.SubServiceTechnicianRepository;
import com.example.servicesprovider.service.SubServiceTechnicianService;

public class SubServiceTechnicianServiceImpl implements SubServiceTechnicianService {

    SubServiceTechnicianRepository repository;

    public SubServiceTechnicianServiceImpl(SubServiceTechnicianRepository repository) {
        this.repository = repository;
    }

    @Override
    public SubServiceTechnician save(SubServiceTechnician subServiceTechnician) {
        return repository.save(subServiceTechnician);
    }

    @Override
    public SubServiceTechnician update(SubServiceTechnician subServiceTechnician) {
        return repository.save(subServiceTechnician);
    }

    @Override
    public void delete(SubServiceTechnician subServiceTechnician) {
        repository.delete(subServiceTechnician);
    }
}
