package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.exception.DuplicateSubServiceNameException;
import com.example.servicesprovider.exception.GeneralServiceNotExistException;
import com.example.servicesprovider.exception.TechnicianNotConfirmedYetException;
import com.example.servicesprovider.model.*;
import com.example.servicesprovider.model.enumeration.TechnicianStatus;
import com.example.servicesprovider.repository.AdminRepository;
import com.example.servicesprovider.service.*;

import javax.validation.Validator;
import java.util.List;

public class AdminServiceImpl extends BaseServiceImpl<Admin, Long, AdminRepository> implements AdminService {
    UserService userService;
    GeneralService_Service generalService_service;
    SubService_Service subService_service;
    TechnicianService technicianService;

    public AdminServiceImpl(AdminRepository repository, Validator validator, UserService userService, GeneralService_Service generalService_service, SubService_Service subService_service, TechnicianService technicianService) {
        super(repository, validator);
        this.userService = userService;
        this.generalService_service = generalService_service;
        this.subService_service = subService_service;
        this.technicianService = technicianService;
    }

    @Override
    public GeneralService addGeneralServiceByAdmin(GeneralService generalService) {
        return generalService_service.save(generalService);
    }

    @Override
    public List<GeneralService> generalServicesListByAdmin() {
        return generalService_service.findAll();
    }

    @Override
    public SubService addSubServiceByAdmin(SubService subService) {
        try {
            SubService subService1 = subService_service.findSubServiceByName(subService.getSubServiceName());
            if (subService1 != null) throw new DuplicateSubServiceNameException("subService name already exist");
            if (subService.getGeneralService() == null)
                throw new GeneralServiceNotExistException("General service no exist");
            return subService_service.save(subService);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Technician addTechnicianByAdmin(Technician technician) {
        try {
            return technicianService.save(technician);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteTechnicianByAdmin(String userName) {
        User technician = userService.findByUserName(userName);
        userService.delete(technician);
    }

    @Override
    public void changePassword(String userName, String password, String newPassword, String duplicateNewPassword) {
        userService.changePassword(userName, password, newPassword, duplicateNewPassword);
    }

    @Override
    public List<Technician> seeTechnicianNotAcceptedByAdmin() {
        return technicianService.findAll();
    }

    @Override
    public void addTechnicianToSubService(Technician technician, SubService subService) {
        try {
            subService.getTechnicianList().add(technician);
            if (technician.getTechnicianStatus().equals(TechnicianStatus.NEW))
                throw new TechnicianNotConfirmedYetException("technician not confirmed yet");
            subService_service.update(subService);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}