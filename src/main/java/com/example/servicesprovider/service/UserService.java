package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.User;

public interface UserService extends BaseService<User, Long> {

    User findByUserName(String userName);

    void changePassword(String userName, String password, String newPassword, String duplicateNewPassword);

    User register(User user);

    User userAuthentication(String userName, String password);
}