package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.model.SubService;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubServiceRepository extends BaseRepository<SubService, Long> {

    SubService findBySubServiceName(String name);

    boolean existsBySubServiceName(String subServiceName);

    @Query("SELECT s FROM SubService s JOIN s.subServiceTechnicianList st WHERE st.technician.id = :technicianId")
    List<SubService> findSubServicesByTechnicianId(Long technicianId);

}