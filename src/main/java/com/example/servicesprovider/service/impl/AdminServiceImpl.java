package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.exception.*;
import com.example.servicesprovider.model.*;
import com.example.servicesprovider.model.enumeration.TechnicianStatus;
import com.example.servicesprovider.repository.AdminRepository;
import com.example.servicesprovider.service.*;
import com.example.servicesprovider.utility.HashGenerator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, Long, AdminRepository> implements AdminService {
    GeneralService_Service generalService_service;
    SubService_Service subService_service;
    TechnicianService technicianService;
    SubServiceTechnicianService subServiceTechnicianService;
    HashGenerator hashGenerator;

    public AdminServiceImpl(AdminRepository repository, GeneralService_Service generalService_service, SubService_Service subService_service, TechnicianService technicianService, SubServiceTechnicianService subServiceTechnicianService, HashGenerator hashGenerator) {
        super(repository);
        this.generalService_service = generalService_service;
        this.subService_service = subService_service;
        this.technicianService = technicianService;
        this.subServiceTechnicianService = subServiceTechnicianService;
        this.hashGenerator = hashGenerator;
    }

    @Override
    public GeneralService addGeneralService(GeneralService generalService) {
        if (generalService_service.existByServiceName(generalService.getServiceName()))
            throw new DuplicateGeneralServiceNameException("General service already exist");
        return generalService_service.save(generalService);
    }

    @Override
    public void deleteByUserName(String userName) {
        repository.deleteByUserName(userName);
    }

    @Override
    public List<GeneralService> generalServicesList() {
        return generalService_service.findAll();
    }

    @Override
    public SubService addSubService(SubService subService) {
        if (subService_service.existBySubServiceName(subService.getSubServiceName()))
            throw new DuplicateSubServiceNameException("SubService already exist");
        if (subService.getGeneralService() == null)
            throw new GeneralServiceNotExistException("General service not exist");
        return subService_service.save(subService);
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
    public List<Technician> seeTechnicianNotAccepted() {
        return technicianService.notConfirmedYet();
    }

    @Override
    public SubServiceTechnician addTechnicianToSubService(Technician technician, SubService subService) {
        if (technician.getTechnicianStatus().equals(TechnicianStatus.NEW)
                || technician.getTechnicianStatus().equals(TechnicianStatus.PENDING_CONFIRMATION))
            throw new TechnicianNotConfirmedYetException("Technician not confirmed yet");
        SubServiceTechnician subServiceTechnician = SubServiceTechnician.builder().subService(subService).technician(technician).build();
        return subServiceTechnicianService.save(subServiceTechnician);
    }

    @Override
    public Admin save(Admin admin) {
        admin.setPassword(hashGenerator.generateSHA512Hash(admin.getPassword()));
        repository.save(admin);
        return admin;
    }

    @Override
    public void deleteTechnicianFromSubService(Technician technician, SubService subService) {
        subServiceTechnicianService.deleteByTechnicianAndSubService(technician, subService);
    }

    @Override
    public Admin findByUserName(String userName) {
        Admin admin = repository.findByUserName(userName);
        if (admin == null) throw new EntityNotFoundException("Model not exist");
        return admin;
    }

    @Override
    public Technician confirmTechnician(Technician technician) {
        technician.setTechnicianStatus(TechnicianStatus.CONFIRMED);
        return technicianService.update(technician);
    }

    @Override
    public Admin adminAuthentication(String userName, String password) {
        Admin admin;
        String hashedPassword = hashGenerator.generateSHA512Hash(password);
        admin = repository.findByUserNameAndPassword(userName, hashedPassword);
        if (admin == null)
            throw new UsernameOrPasswordNotCorrectException("Username or password not correct");
        return admin;
    }
}