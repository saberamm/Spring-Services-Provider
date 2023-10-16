package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.model.Technician;
import com.example.servicesprovider.repository.TechnicianRepository;
import com.example.servicesprovider.service.TechnicianService;
import com.example.servicesprovider.service.UserService;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
@Service
public class TechnicianServiceImpl extends BaseServiceImpl<Technician, Long, TechnicianRepository> implements TechnicianService {
    UserService userService;

    public TechnicianServiceImpl(TechnicianRepository repository, Validator validator) {
        super(repository, validator);
    }

    @Override
    public void changePassword(String userName, String password, String newPassword, String duplicateNewPassword) {
        userService.changePassword(userName, password, newPassword,duplicateNewPassword);
    }
}