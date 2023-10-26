package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.model.SubService;
import com.example.servicesprovider.repository.SubServiceRepository;
import com.example.servicesprovider.service.SubService_Service;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubService_ServiceImpl extends BaseServiceImpl<SubService, Long, SubServiceRepository> implements SubService_Service {

    public SubService_ServiceImpl(SubServiceRepository repository) {
        super(repository);
    }

    @Override
    public SubService findBySubServiceName(String name) {
        return repository.findBySubServiceName(name);
    }

    @Override
    public boolean existBySubServiceName(String subServiceName) {
        return repository.existsBySubServiceName(subServiceName);
    }

    @Override
    public List<SubService> findSubServicesByTechnicianId(Long technicianId) {
        return repository.findSubServicesByTechnicianId(technicianId);
    }
}