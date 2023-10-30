package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.model.GeneralService;
import com.example.servicesprovider.repository.GeneralServiceRepository;
import com.example.servicesprovider.service.GeneralService_Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GeneralService_ServiceImpl extends BaseServiceImpl<GeneralService, Long, GeneralServiceRepository> implements GeneralService_Service {

    public GeneralService_ServiceImpl(GeneralServiceRepository repository) {
        super(repository);
    }

    @Override
    public boolean existByServiceName(String serviceName) {
        return repository.existsByServiceName(serviceName);
    }

    @Override
    public void deleteByServiceName(String serviceName) {
        repository.deleteByServiceName(serviceName);
    }

    @Override
    public GeneralService findByServiceName(String serviceName) {
        GeneralService generalService = repository.findByServiceName(serviceName);
        if (generalService == null) throw new EntityNotFoundException("Model not exist");
        return generalService;
    }
}