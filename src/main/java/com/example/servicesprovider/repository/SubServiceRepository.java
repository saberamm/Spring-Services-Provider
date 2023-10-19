package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.model.SubService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubServiceRepository extends BaseRepository<SubService, Long> {
    SubService findBySubServiceName(String name);
    List<SubService> findByTechnicianListId(Long technicianList_id);
}