package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.exception.*;
import com.example.servicesprovider.model.Offer;
import com.example.servicesprovider.model.Order;
import com.example.servicesprovider.model.SubService;
import com.example.servicesprovider.model.Technician;
import com.example.servicesprovider.model.enumeration.OrderStatus;
import com.example.servicesprovider.model.enumeration.TechnicianStatus;
import com.example.servicesprovider.repository.TechnicianRepository;
import com.example.servicesprovider.service.OfferService;
import com.example.servicesprovider.service.OrderService;
import com.example.servicesprovider.service.SubService_Service;
import com.example.servicesprovider.service.TechnicianService;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TechnicianServiceImpl extends BaseServiceImpl<Technician, Long, TechnicianRepository> implements TechnicianService {
    OfferService offerService;
    OrderService orderService;
    SubService_Service subService_service;

    public TechnicianServiceImpl(TechnicianRepository repository, Validator validator, OfferService offerService, OrderService orderService, SubService_Service subService_service) {
        super(repository, validator);
        this.offerService = offerService;
        this.orderService = orderService;
        this.subService_service = subService_service;
    }

    @Override
    public Technician findByUserName(String userName) {
        Technician technician = repository.findByUserName(userName);
        if (technician == null) {
            throw new RuntimeException("Technician not found with username: " + userName);
        }
        return technician;
    }

    @Override
    public void deleteByUserName(String userName) {
        try {
            repository.deleteByUserName(userName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Offer addOffer(Offer offer, Technician technician) {
        Order order = offer.getOrder();
        try {
            if (offer.getTimeForStartWorking().isBefore(order.getWorkTime()))
                throw new OfferTimeBeforeOrderTimeException("offer time can not be before order time");
            if (technician.getTechnicianStatus().equals(TechnicianStatus.NEW)
                    || technician.getTechnicianStatus().equals(TechnicianStatus.PENDING_CONFIRMATION))
                throw new TechnicianNotConfirmedYetException("Technician not confirmed yet");
            if (offer.getSuggestionPrice() < order.getSubService().getBasePrice())
                throw new PriceIsLowerThanBasePriceException("Suggestion price can not be lower than sub service base price");
            offer.getOrder().setOrderStatus(OrderStatus.WAITING_FOR_CHOOSING_TECHNICIAN);
            order.setOrderStatus(OrderStatus.WAITING_FOR_CHOOSING_TECHNICIAN);
            orderService.update(offer.getOrder());
            return offerService.save(offer);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Order> OrdersThatTechnicianCanOffer(Technician technician) {
        List<SubService> subServices = subService_service.findSubServicesByTechnicianId(technician.getId());

        List<Order> orders2 = subServices.stream()
                .flatMap(subService -> orderService.findBySubService(subService).stream())
                .filter(order ->
                        order.getOrderStatus() == OrderStatus.WAITING_FOR_TECHNICIAN_OFFER
                                || order.getOrderStatus() == OrderStatus.WAITING_FOR_CHOOSING_TECHNICIAN)
                .collect(Collectors.toList());
        try {
            if (orders2.isEmpty()) throw new NoOrdersFoundException("there is no order to offer");
        } catch (NoOrdersFoundException e) {
            System.out.println(e.getMessage());
        }

        return orders2;
    }


    @Override
    public List<Technician> notConfirmedYet() {
        List<Technician> technicians = new ArrayList<>();
        try {
            List<Technician> techniciansNew = repository.findAllByTechnicianStatus(TechnicianStatus.NEW);
            List<Technician> techniciansPending = repository.findAllByTechnicianStatus(TechnicianStatus.PENDING_CONFIRMATION);
            technicians.addAll(techniciansNew);
            technicians.addAll(techniciansPending);
            return technicians;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}