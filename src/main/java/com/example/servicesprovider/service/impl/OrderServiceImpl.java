package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.model.Order;
import com.example.servicesprovider.model.SubService;
import com.example.servicesprovider.repository.OrderRepository;
import com.example.servicesprovider.service.OrderService;

import javax.validation.Validator;
import java.util.List;

public class OrderServiceImpl extends BaseServiceImpl<Order, Long, OrderRepository> implements OrderService {
    public OrderServiceImpl(OrderRepository repository, Validator validator) {
        super(repository, validator);
    }

    @Override
    public List<Order> findBySubService(SubService subService) {
        return repository.findAllBySubServiceId(subService.getId());
    }
}