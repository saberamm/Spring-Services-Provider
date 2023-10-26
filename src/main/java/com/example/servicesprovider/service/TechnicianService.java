package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.Offer;
import com.example.servicesprovider.model.Order;
import com.example.servicesprovider.model.Technician;

import java.util.List;

public interface TechnicianService extends BaseService<Technician, Long> {
    Technician findByUserName(String userName);

    void deleteByUserName(String userName);

    Offer addOffer(Offer offer, Technician technician);

    List<Order> OrdersThatTechnicianCanOffer(Technician technician);

    List<Technician> notConfirmedYet();

    Technician technicianAuthentication(String userName, String password);
}