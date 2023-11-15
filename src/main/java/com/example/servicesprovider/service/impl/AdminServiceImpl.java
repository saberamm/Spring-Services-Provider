package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.exception.*;
import com.example.servicesprovider.model.*;
import com.example.servicesprovider.model.enumeration.TechnicianStatus;
import com.example.servicesprovider.repository.AdminRepository;
import com.example.servicesprovider.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, Long, AdminRepository> implements AdminService {
    GeneralService_Service generalService_service;
    SubService_Service subService_service;
    TechnicianService technicianService;
    SubServiceTechnicianService subServiceTechnicianService;
    PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository repository, GeneralService_Service generalService_service, SubService_Service subService_service, TechnicianService technicianService, SubServiceTechnicianService subServiceTechnicianService, PasswordEncoder passwordEncoder) {
        super(repository);
        this.generalService_service = generalService_service;
        this.subService_service = subService_service;
        this.technicianService = technicianService;
        this.subServiceTechnicianService = subServiceTechnicianService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public GeneralService addGeneralService(GeneralService generalService) {
        if (generalService_service.existByServiceName(generalService.getServiceName()))
            throw new DuplicateGeneralServiceNameException("General service already exist");
        return generalService_service.save(generalService);
    }

    @Override
    @Transactional
    public void deleteByUserName(String userName) {
        Admin admin = repository.findByUserName(userName);
        if (admin != null) {
            repository.deleteByUserName(userName);
        }
    }

    @Override
    @Transactional
    public List<GeneralService> generalServicesList() {
        return generalService_service.findAll();
    }

    @Override
    @Transactional
    public SubService addSubService(SubService subService) {
        if (subService_service.existBySubServiceName(subService.getSubServiceName()))
            throw new DuplicateSubServiceNameException("SubService already exist");
        if (subService.getGeneralService() == null)
            throw new GeneralServiceNotExistException("General service not exist");
        return subService_service.save(subService);
    }

    @Override
    @Transactional
    public Technician addTechnician(Technician technician) {
        return technicianService.save(technician);
    }

    @Override
    @Transactional
    public void deleteTechnician(String userName) {
        technicianService.deleteByUserName(userName);
    }

    @Override
    @Transactional
    public List<Technician> seeTechnicianNotAccepted() {
        return technicianService.notConfirmedYet();
    }

    @Override
    @Transactional
    public SubServiceTechnician addTechnicianToSubService(Technician technician, SubService subService) {
        if (technician.getTechnicianStatus().equals(TechnicianStatus.NEW)
                || technician.getTechnicianStatus().equals(TechnicianStatus.PENDING_CONFIRMATION))
            throw new TechnicianNotConfirmedYetException("Technician not confirmed yet");
        SubServiceTechnician subServiceTechnician = SubServiceTechnician.builder().subService(subService).technician(technician).build();
        return subServiceTechnicianService.save(subServiceTechnician);
    }

    @Override
    @Transactional
    public SubService updateSubServicePrice(Long subServiceId, Double basePrice) {
        SubService subService = subService_service.findById(subServiceId);
        subService.setBasePrice(basePrice);
        return subService_service.update(subService);
    }

    @Override
    @Transactional
    public SubService updateSubServiceName(Long subServiceId, String subServiceName) {
        SubService subService = subService_service.findById(subServiceId);
        subService.setSubServiceName(subServiceName);
        return subService_service.update(subService);
    }

    @Override
    @Transactional
    public Admin save(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setConfirmationToken(UUID.randomUUID().toString());
        repository.save(admin);
        return admin;
    }

    @Override
    @Transactional
    public void deleteTechnicianFromSubService(Technician technician, SubService subService) {
        subServiceTechnicianService.deleteByTechnicianAndSubService(technician, subService);
    }

    @Override
    @Transactional
    public Admin findByUserName(String userName) {
        Admin admin = repository.findByUserName(userName);
        if (admin == null) throw new EntityNotFoundException("Admin model not exist");
        return admin;
    }

    @Override
    @Transactional
    public Technician confirmTechnician(Technician technician) {
        technician.setTechnicianStatus(TechnicianStatus.CONFIRMED);
        return technicianService.update(technician);
    }

    @Override
    @Transactional
    public Admin adminAuthentication(String userName, String password) {
        Admin admin;
        String hashedPassword = passwordEncoder.encode(password);
        admin = repository.findByUserNameAndPassword(userName, hashedPassword);
        if (admin == null)
            throw new UsernameOrPasswordNotCorrectException("Username or password not correct");
        return admin;
    }
}