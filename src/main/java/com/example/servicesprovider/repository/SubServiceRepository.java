package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.model.SubService;

import java.util.List;

public interface SubServiceRepository extends BaseRepository<SubService, Long> {

    SubService findBySubServiceName(String name);

    boolean existsBySubServiceName (String subServiceName);

    List<SubService> findByTechnicianListId(Long technicianList_id);
}