package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.model.Client;

public interface ClientRepository extends BaseRepository<Client, Long> {

    Client findByUserName(String userName);

    Client findByUserNameAndPassword(String userName, String password);
}