package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.*;

import java.util.List;

public interface ClientService extends BaseService<Client, Long> {

    void changePassword(String userName, String password, String newPassword, String duplicateNewPassword);

    void addOrderByClient(Order order, Client client);

    List<GeneralService> seeGeneralServicesByClient();

    List<SubService> seeSubServicesByClient();

    Client findByUserName(String userName);

    Client userAuthentication(String userName, String password);
}