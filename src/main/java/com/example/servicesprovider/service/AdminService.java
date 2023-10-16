package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.*;

import java.util.List;

public interface AdminService extends BaseService<Admin, Long> {
    GeneralService addGeneralServiceByAdmin(GeneralService generalService);

    List<GeneralService> generalServicesListByAdmin();

    SubService addSubServiceByAdmin(SubService subService);

    Technician addTechnicianByAdmin(Technician technician);

    void deleteTechnicianByAdmin(String userName);

    void changePassword(String userName, String password, String newPassword, String duplicateNewPassword);

    List<Technician> seeTechnicianNotAcceptedByAdmin();

    void addTechnicianToSubService(Technician technician, SubService subService);

    Admin findByUserName(String userName);

    Admin userAuthentication(String userName, String password);
}