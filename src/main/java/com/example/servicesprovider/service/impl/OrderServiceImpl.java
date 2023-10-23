package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.model.Order;
import com.example.servicesprovider.model.SubService;
import com.example.servicesprovider.repository.OrderRepository;
import com.example.servicesprovider.service.OrderService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Long, OrderRepository> implements OrderService {
    public OrderServiceImpl(OrderRepository repository, Validator validator) {
        super(repository, validator);
    }

    @Override
    public List<Order> findBySubService(SubService subService) {
        try {
            return repository.findAllBySubServiceId(subService.getId());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}