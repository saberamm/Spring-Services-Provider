package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.model.Order;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order, Long> {

    List<Order> findAllBySubServiceId(Long subServiceId);
}