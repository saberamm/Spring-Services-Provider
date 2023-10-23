package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long> {

    List<Order> findAllBySubServiceId(Long subServiceId);
}