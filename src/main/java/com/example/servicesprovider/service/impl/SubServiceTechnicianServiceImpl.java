package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.model.SubServiceTechnician;
import com.example.servicesprovider.repository.SubServiceTechnicianRepository;
import com.example.servicesprovider.service.SubServiceTechnicianService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SubServiceTechnicianServiceImpl implements SubServiceTechnicianService {

    SubServiceTechnicianRepository repository;

    public SubServiceTechnicianServiceImpl(SubServiceTechnicianRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public SubServiceTechnician save(SubServiceTechnician subServiceTechnician) {
        return repository.save(subServiceTechnician);
    }

    @Override
    @Transactional
    public SubServiceTechnician update(SubServiceTechnician subServiceTechnician) {
        return repository.save(subServiceTechnician);
    }

    @Override
    @Transactional
    public void delete(SubServiceTechnician subServiceTechnician) {
        repository.delete(subServiceTechnician);
    }

    @Override
    @Transactional
    public void deleteByTechnicianAndSubService(Long technicianId, Long subServiceId) {
        SubServiceTechnician subServiceTechnician = repository.findByTechnician_IdAndSubService_Id(technicianId, subServiceId);
        if (subServiceTechnician == null) {
            throw new EntityNotFoundException("Technician SubService model not exist");
        }
        repository.delete(subServiceTechnician);
    }
}
