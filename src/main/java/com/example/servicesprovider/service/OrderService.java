package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.Order;
import com.example.servicesprovider.model.SubService;

import java.util.List;

public interface OrderService extends BaseService<Order, Long> {
    List<Order> findBySubService(SubService subService);
}