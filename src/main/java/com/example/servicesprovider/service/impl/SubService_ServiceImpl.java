package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.model.SubService;
import com.example.servicesprovider.repository.SubServiceRepository;
import com.example.servicesprovider.service.SubService_Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubService_ServiceImpl extends BaseServiceImpl<SubService, Long, SubServiceRepository> implements SubService_Service {

    public SubService_ServiceImpl(SubServiceRepository repository) {
        super(repository);
    }

    @Override
    public SubService findBySubServiceName(String name) {
        SubService subService = repository.findBySubServiceName(name);
        if (subService == null) throw new EntityNotFoundException("Model not exist");
        return subService;
    }

    @Override
    public boolean existBySubServiceName(String subServiceName) {
        return repository.existsBySubServiceName(subServiceName);
    }

    @Override
    public List<SubService> findSubServicesByTechnicianId(Long technicianId) {
        List<SubService> subServices = repository.findSubServicesByTechnicianId(technicianId);
        if (subServices == null) throw new EntityNotFoundException("Model not exist");
        return subServices;
    }
}