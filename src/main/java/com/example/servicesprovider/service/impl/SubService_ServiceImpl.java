package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.model.SubService;
import com.example.servicesprovider.repository.SubServiceRepository;
import com.example.servicesprovider.service.SubService_Service;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
@Service
public class SubService_ServiceImpl extends BaseServiceImpl<SubService, Long, SubServiceRepository> implements SubService_Service {

    public SubService_ServiceImpl(SubServiceRepository repository, Validator validator) {
        super(repository, validator);
    }

    @Override
    public SubService findSubServiceByName(String name) {
        try {
            return repository.findBySubServiceName(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}