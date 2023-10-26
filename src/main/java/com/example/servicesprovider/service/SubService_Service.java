package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.SubService;

import java.util.List;

public interface SubService_Service extends BaseService<SubService, Long> {
    SubService findBySubServiceName(String name);

    boolean existBySubServiceName(String subServiceName);

    List<SubService> findSubServicesByTechnicianId(Long technicianId);
}