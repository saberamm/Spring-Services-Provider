package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.*;

import java.util.List;

public interface ClientService extends BaseService<Client, Long> {


    Order addOrder(Order order);

    List<GeneralService> seeGeneralServices();

    void deleteByUserName(String userName);

    List<SubService> seeSubServices();

    Client findByUserName(String userName);

    Order chooseOffer(Offer offer);

    Order startOrder(Order order);

    Order completeOrder(Order order);

    void addOverallScore(Technician technician);

    void addNegativeScore(Offer offer, Technician technician);

    boolean isDoneAfterEndTime(Order order, Offer offer);

    Client clientAuthentication(String userName, String password);

    void payWithClientCredit(Offer offer, Client client);

    void payWithCreditCard(CreditCard creditCard, Offer offer);

    boolean checkCreditCards(CreditCard savedCreditCard, CreditCard creditCard);
}