package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.Technician;

public interface TechnicianService extends BaseService<Technician, Long> {
    void changePassword(String userName, String password,String newPassword,String duplicateNewPassword);
}