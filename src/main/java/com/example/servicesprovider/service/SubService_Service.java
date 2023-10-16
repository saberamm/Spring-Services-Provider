package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.SubService;

public interface SubService_Service extends BaseService<SubService, Long> {
    SubService findSubServiceByName(String name);
}