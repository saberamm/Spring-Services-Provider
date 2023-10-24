package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.model.Technician;
import com.example.servicesprovider.model.enumeration.TechnicianStatus;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface TechnicianRepository extends BaseRepository<Technician, Long> {

    Technician findByUserName(String userName);

    Technician findByUserNameAndPassword(String userName, String password);

    void deleteByUserName(String userName);

    List<Technician> findAllByTechnicianStatus(@NotNull(message = "technicianStatus cannot be null") TechnicianStatus technicianStatus);
}