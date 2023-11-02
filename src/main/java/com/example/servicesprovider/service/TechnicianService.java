package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.Offer;
import com.example.servicesprovider.model.Order;
import com.example.servicesprovider.model.Technician;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TechnicianService extends BaseService<Technician, Long> {
    Technician findByUserName(String userName);

    void deleteByUserName(String userName);

    Offer addOffer(Offer offer);

    List<Order> ordersThatTechnicianCanOffer(Technician technician);

    List<Technician> notConfirmedYet();

    Technician technicianAuthentication(String userName, String password);

    @Transactional
    Double getOverallScore(Long technicianId);
}