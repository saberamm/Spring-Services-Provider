package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.model.GeneralService;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralServiceRepository extends BaseRepository<GeneralService, Long> {

    GeneralService findByServiceName(String name);

    boolean existsByServiceName(String ServiceName);
}