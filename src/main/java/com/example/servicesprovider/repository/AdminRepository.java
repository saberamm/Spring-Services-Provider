package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.model.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends BaseRepository<Admin, Long> {

    Admin findByUserName(String userName);

    Admin findByUserNameAndPassword(String userName, String password);
}