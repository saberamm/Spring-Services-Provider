package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.model.SubService;
import org.springframework.stereotype.Repository;

@Repository
public interface SubServiceRepository extends BaseRepository<SubService, Long> {
    SubService findBySubServiceName(String name);
}