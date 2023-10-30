package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.GeneralService;

public interface GeneralService_Service extends BaseService<GeneralService, Long> {
    boolean existByServiceName(String serviceName);

    void deleteByServiceName(String serviceName);

    GeneralService findByServiceName(String serviceName);
}