package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AdminService extends BaseService<Admin, Long> {
    GeneralService addGeneralService(GeneralService generalService);

    List<GeneralService> generalServicesList();

    SubService addSubService(SubService subService);

    Technician addTechnician(Technician technician);

    void deleteTechnician(String userName);

    List<Technician> seeTechnicianNotAccepted();

    SubServiceTechnician addTechnicianToSubService(Technician technician, SubService subService);

    void deleteTechnicianFromSubService(Technician technician, SubService subService);

    Admin findByUserName(String userName);

    Technician confirmTechnician(Technician technician);

    Admin adminAuthentication(String userName, String password);
}