package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.Offer;
import com.example.servicesprovider.model.Order;
import jakarta.transaction.Transactional;

import java.util.List;

public interface OfferService extends BaseService<Offer, Long> {
    List<Offer> getOffersSortedByTechnicianScore(Order order);

    List<Offer> getOffersSortedByPrice(Order order);

    @Transactional
    List<Offer> filterOffersByCriteria(Long orderId, String storeBy);
}