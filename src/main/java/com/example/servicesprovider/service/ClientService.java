package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.*;

import java.util.List;

public interface ClientService extends BaseService<Client, Long> {

    void changePassword(String userName, String password, String newPassword, String duplicateNewPassword);

    void addOrder(Order order, Client client);

    List<GeneralService> seeGeneralServices();

    List<SubService> seeSubServices();

    Client findByUserName(String userName);

    Client userAuthentication(String userName, String password);

    Order chooseOffer(Order order, Offer offer);

    Order startOrder(Order order);

    Order completeOrder(Order order);
}