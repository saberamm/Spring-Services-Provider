package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.exception.*;
import com.example.servicesprovider.model.*;
import com.example.servicesprovider.model.enumeration.OrderStatus;
import com.example.servicesprovider.model.enumeration.TechnicianStatus;
import com.example.servicesprovider.repository.TechnicianRepository;
import com.example.servicesprovider.service.OfferService;
import com.example.servicesprovider.service.OrderService;
import com.example.servicesprovider.service.SubService_Service;
import com.example.servicesprovider.service.TechnicianService;
import com.example.servicesprovider.utility.HashGenerator;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TechnicianServiceImpl extends BaseServiceImpl<Technician, Long, TechnicianRepository> implements TechnicianService {
    OfferService offerService;
    OrderService orderService;
    SubService_Service subService_service;
    HashGenerator hashGenerator;

    public TechnicianServiceImpl(TechnicianRepository repository, OfferService offerService, OrderService orderService, SubService_Service subService_service, HashGenerator hashGenerator) {
        super(repository);
        this.offerService = offerService;
        this.orderService = orderService;
        this.subService_service = subService_service;
        this.hashGenerator = hashGenerator;
    }

    @Override
    @Transactional
    public Technician findByUserName(String userName) {
        Technician technician=repository.findByUserName(userName);
        if (technician==null) throw new EntityNotFoundException("Model not exist");
        return technician;
    }

    @Override
    @Transactional
    public void deleteByUserName(String userName) {
        repository.deleteByUserName(userName);
    }

    @Override
    @Transactional
    public Offer addOffer(Offer offer, Technician technician) {
        Order order = offer.getOrder();
        if (offer.getTimeForStartWorking().isBefore(order.getWorkTime()))
            throw new OfferTimeBeforeOrderTimeException("offer time can not be before order time");
        if (technician.getTechnicianStatus().equals(TechnicianStatus.NEW)
                || technician.getTechnicianStatus().equals(TechnicianStatus.PENDING_CONFIRMATION)
                || technician.getTechnicianStatus().equals(TechnicianStatus.INACTIVE))
            throw new TechnicianNotConfirmedYetException("Technician not confirmed yet");
        if (offer.getSuggestionPrice() < order.getSubService().getBasePrice())
            throw new PriceIsLowerThanBasePriceException("Suggestion price can not be lower than sub service base price");
        order.setOrderStatus(OrderStatus.WAITING_FOR_CHOOSING_TECHNICIAN);
        orderService.update(order);
        return offerService.save(offer);
    }

    @Override
    @Transactional
    public List<Order> OrdersThatTechnicianCanOffer(Technician technician) {
        List<SubService> subServices = subService_service.findSubServicesByTechnicianId(technician.getId());

        List<Order> orders = subServices.stream()
                .flatMap(subService -> orderService.findBySubService(subService).stream())
                .filter(order ->
                        order.getOrderStatus() == OrderStatus.WAITING_FOR_TECHNICIAN_OFFER
                                || order.getOrderStatus() == OrderStatus.WAITING_FOR_CHOOSING_TECHNICIAN)
                .collect(Collectors.toList());
        if (orders.isEmpty()) throw new NoOrdersFoundException("there is no order to offer");
        return orders;
    }


    @Override
    @Transactional
    public List<Technician> notConfirmedYet() {
        return repository.findAllByTechnicianStatus(TechnicianStatus.PENDING_CONFIRMATION);
    }

    @Override
    @Transactional
    public Technician save(Technician technician) {
        technician.setPassword(hashGenerator.generateSHA512Hash(technician.getPassword()));
        repository.save(technician);
        return technician;
    }

    @Override
    @Transactional
    public Technician technicianAuthentication(String userName, String password) {
        Technician technician;
        String hashedPassword = hashGenerator.generateSHA512Hash(password);
        technician = repository.findByUserNameAndPassword(userName, hashedPassword);
        if (technician == null)
            throw new UsernameOrPasswordNotCorrectException("Username or password not correct");
        return technician;
    }
}