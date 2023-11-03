package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService extends BaseService<User, Long> {

    User findByUserName(String userName);

    void changePassword(String userName, String password, String newPassword, String duplicateNewPassword);

    User userAuthentication(String userName, String password);

    @Transactional
    Page<User> searchAndFilterUsers(String role, String name, String surname, String email, String sortBy, Pageable pageable);
}