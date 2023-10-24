package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.model.User;

public interface UserRepository extends BaseRepository<User, Long> {

    User findByUserName(String userName);

    User findByUserNameAndPassword(String userName, String password);
}