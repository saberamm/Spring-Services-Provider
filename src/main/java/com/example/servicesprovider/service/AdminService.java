package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.*;

import java.util.List;

public interface AdminService extends BaseService<Admin, Long> {
    GeneralService addGeneralService(GeneralService generalService);

    List<GeneralService> generalServicesList();

    SubService addSubService(SubService subService);

    Technician addTechnician(Technician technician);

    void deleteTechnician(String userName);

    void changePassword(String userName, String password, String newPassword, String duplicateNewPassword);

    List<Technician> seeTechnicianNotAccepted();

    void addTechnicianToSubService(Technician technician, SubService subService);

    Admin findByUserName(String userName);

    Admin userAuthentication(String userName, String password);

    Technician confirmTechnician(Technician technician);
}