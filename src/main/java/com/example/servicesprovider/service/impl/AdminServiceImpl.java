package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.exception.*;
import com.example.servicesprovider.model.*;
import com.example.servicesprovider.model.enumeration.TechnicianStatus;
import com.example.servicesprovider.repository.AdminRepository;
import com.example.servicesprovider.service.*;

import javax.validation.Validator;
import java.util.List;

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
        if (generalService_service.existByServiceName(generalService.getServiceName()))
            throw new DuplicateGeneralServiceNameException("General service already exist");
        return generalService_service.save(generalService);
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
    public void addTechnicianToSubService(Technician technician, SubService subService) {
        List<SubService> subServices = subService_service.findSubServicesByTechnicianId(technician.getId());
        subServices.add(subService);
        technician.setSubServiceList(subServices);
        if (technician.getTechnicianStatus().equals(TechnicianStatus.NEW)
                || technician.getTechnicianStatus().equals(TechnicianStatus.PENDING_CONFIRMATION))
            throw new TechnicianNotConfirmedYetException("Technician not confirmed yet");
        technicianService.update(technician);
    }

    @Override
    public Admin findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    @Override
    public Technician confirmTechnician(Technician technician) {
        technician.setTechnicianStatus(TechnicianStatus.CONFIRMED);
        return technicianService.update(technician);
    }
}