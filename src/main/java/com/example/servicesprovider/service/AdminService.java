package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.Admin;
import com.example.servicesprovider.model.GeneralService;
import com.example.servicesprovider.model.SubService;
import com.example.servicesprovider.model.Technician;

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
}