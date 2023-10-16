package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.model.Technician;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicianRepository extends BaseRepository<Technician, Long> {
    Technician findByUserName(String userName);

    Technician findByUserNameAndPassword(String userName, String password);
}