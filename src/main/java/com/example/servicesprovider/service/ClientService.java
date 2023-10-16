package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.Client;
import com.example.servicesprovider.model.GeneralService;
import com.example.servicesprovider.model.Order;
import com.example.servicesprovider.model.SubService;

import java.util.List;

public interface ClientService extends BaseService<Client, Long> {
    void changePassword(String userName, String password, String newPassword, String duplicateNewPassword);

    void addOrderByClient(Order order, Client client);

    List<GeneralService> seeGeneralServicesByClient();

    List<SubService> seeSubServicesByClient();
}