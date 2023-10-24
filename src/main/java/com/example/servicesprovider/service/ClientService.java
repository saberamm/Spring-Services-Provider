package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ClientService extends BaseService<Client, Long> {


    Order addOrder(Order order, Client client);

    List<GeneralService> seeGeneralServices();

    List<SubService> seeSubServices();

    Client findByUserName(String userName);

    Order chooseOffer(Order order, Offer offer);

    Order startOrder(Order order);

    Order completeOrder(Order order);

    Client clientAuthentication(String userName, String password);
}