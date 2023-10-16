package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.model.ViewPoint;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewPointRepository extends BaseRepository<ViewPoint, Long> {
}