package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.model.Technician;
import com.example.servicesprovider.repository.TechnicianRepository;
import com.example.servicesprovider.service.TechnicianService;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
@Service
public class TechnicianServiceImpl extends BaseServiceImpl<Technician, Long, TechnicianRepository> implements TechnicianService {

    public TechnicianServiceImpl(TechnicianRepository repository, Validator validator) {
        super(repository, validator);
    }

    @Override
    public void changePassword(String userName, String password, String newPassword, String duplicateNewPassword) {
        Technician technician = userAuthentication(userName, password);
        try {
            technician.setPassword(newPassword);
            update(technician);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Technician findByUserName(String userName) {
        try {
            return repository.findByUserName(userName);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public Technician userAuthentication(String userName, String password) {
        try {
            return repository.findByUserNameAndPassword(userName, password);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}