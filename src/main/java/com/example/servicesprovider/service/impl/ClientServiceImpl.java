package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.exception.*;
import com.example.servicesprovider.model.*;
import com.example.servicesprovider.model.enumeration.OrderStatus;
import com.example.servicesprovider.repository.ClientRepository;
import com.example.servicesprovider.service.*;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClientServiceImpl extends BaseServiceImpl<Client, Long, ClientRepository> implements ClientService {
    OrderService orderService;
    SubService_Service subService_service;
    GeneralService_Service generalService_service;
    OfferService offerService;

    public ClientServiceImpl(ClientRepository repository, Validator validator, OrderService orderService, SubService_Service subService_service, GeneralService_Service generalService_service, OfferService offerService) {
        super(repository, validator);
        this.orderService = orderService;
        this.subService_service = subService_service;
        this.generalService_service = generalService_service;
        this.offerService = offerService;
    }

    @Override
    public Order addOrder(Order order, Client client) {
        if (order.getOrderPrice() < order.getSubService().getBasePrice()) {
            throw new PriceIsLowerThanBasePriceException("Order price should not be smaller than sub service base price");
        }
        order.setClient(client);
        return orderService.save(order);
    }

    @Override
    public List<GeneralService> seeGeneralServices() {
        return generalService_service.findAll();
    }

    @Override
    public List<SubService> seeSubServices() {
        return subService_service.findAll();
    }

    @Override
    public Client findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    @Override
    public Order chooseOffer(Order order, Offer offer) {
        order.setSelectedOffersId(offer.getId());
        order.setOrderStatus(OrderStatus.WAITING_FOR_TECHNICIAN_TO_COME_YOUR_PLACE);
        return orderService.update(order);
    }

    @Override
    public Order startOrder(Order order) {
        Offer offer = offerService.findById(order.getSelectedOffersId());
        if (order.getOrderStatus().equals(OrderStatus.WAITING_FOR_TECHNICIAN_TO_COME_YOUR_PLACE)
                && LocalDateTime.now().isAfter(offer.getTimeForStartWorking())) {
            order.setOrderStatus(OrderStatus.STARTED);
            return orderService.update(order);
        } else {
            throw new OrderHasNoSelectedOfferException("choose an offer first");
        }
    }


    @Override
    public Order completeOrder(Order order) {
        if (order.getOrderStatus().equals(OrderStatus.STARTED)) {
            order.setOrderStatus(OrderStatus.DONE);
            return orderService.update(order);
        } else {
            throw new OrderNotStartedYetException("Order not started");
        }
    }
}