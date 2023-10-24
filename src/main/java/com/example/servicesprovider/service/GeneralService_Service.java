package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.GeneralService;
import org.springframework.stereotype.Service;

@Service
public interface GeneralService_Service extends BaseService<GeneralService, Long> {
    boolean existByServiceName(String serviceName);

    GeneralService findByServiceName(String serviceName);
}