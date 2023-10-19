package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.Offer;
import com.example.servicesprovider.model.Technician;

import java.util.List;

public interface TechnicianService extends BaseService<Technician, Long> {
    void changePassword(String userName, String password,String newPassword,String duplicateNewPassword);
    Technician findByUserName(String userName);
    Technician userAuthentication(String userName, String password);

    void deleteByUserName(String userName);

    Offer addOffer(Offer offer);

    //    public List<Order> OrdersThatTechnicianCanOffer(Technician technician){
    //
    //    }
    List<Technician> notConfirmedYet();
}