package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.exception.DuplicateSubServiceNameException;
import com.example.servicesprovider.exception.GeneralServiceNotExistException;
import com.example.servicesprovider.exception.TechnicianNotConfirmedYetException;
import com.example.servicesprovider.model.*;
import com.example.servicesprovider.model.enumeration.TechnicianStatus;
import com.example.servicesprovider.repository.AdminRepository;
import com.example.servicesprovider.service.*;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, Long, AdminRepository> implements AdminService {
    GeneralService_Service generalService_service;
    SubService_Service subService_service;
    TechnicianService technicianService;

    public AdminServiceImpl(AdminRepository repository, Validator validator, GeneralService_Service generalService_service, SubService_Service subService_service, TechnicianService technicianService) {
        super(repository, validator);
        this.generalService_service = generalService_service;
        this.subService_service = subService_service;
        this.technicianService = technicianService;
    }

    @Override
    public GeneralService addGeneralService(GeneralService generalService) {
        return generalService_service.save(generalService);
    }

    @Override
    public List<GeneralService> generalServicesList() {
        return generalService_service.findAll();
    }

    @Override
    public SubService addSubService(SubService subService) {
        SubService subService1 = subService_service.findSubServiceByName(subService.getSubServiceName());
        try {
            if (subService1 != null)
                throw new DuplicateSubServiceNameException("SubService name already exist");
            if (subService.getGeneralService() == null)
                throw new GeneralServiceNotExistException("General service not exist");
            return subService_service.save(subService);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Technician addTechnician(Technician technician) {
        return technicianService.save(technician);
    }

    @Override
    public void deleteTechnician(String userName) {
        technicianService.deleteByUserName(userName);
    }

    @Override
    public void changePassword(String userName, String password, String newPassword, String duplicateNewPassword) {
        Admin admin = userAuthentication(userName, password);
        try {
            admin.setPassword(newPassword);
            update(admin);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Technician> seeTechnicianNotAccepted() {
        return technicianService.findAll();
    }

    @Override
    public void addTechnicianToSubService(Technician technician, SubService subService) {
        try {
            technician.getSubServiceList().add(subService);
            if (technician.getTechnicianStatus().equals(TechnicianStatus.NEW))
                throw new TechnicianNotConfirmedYetException("Technician not confirmed yet");
            technicianService.update(technician);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Admin findByUserName(String userName) {
        try {
            return repository.findByUserName(userName);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public Admin userAuthentication(String userName, String password) {
        try {
            return repository.findByUserNameAndPassword(userName, password);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}