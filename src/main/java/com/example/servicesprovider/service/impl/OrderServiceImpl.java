package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.dto.OrderFilterRequestDto;
import com.example.servicesprovider.model.Order;
import com.example.servicesprovider.model.SubService;
import com.example.servicesprovider.model.enumeration.OrderStatus;
import com.example.servicesprovider.repository.OrderRepository;
import com.example.servicesprovider.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Long, OrderRepository> implements OrderService {
    public OrderServiceImpl(OrderRepository repository) {
        super(repository);
    }

    @Override
    @Transactional
    public List<Order> findBySubService(SubService subService) {
        List<Order> orders = repository.findAllBySubServiceId(subService.getId());
        if (orders == null) throw new EntityNotFoundException("Model not exist");
        return orders;
    }

    @Override
    @Transactional
    public Page<Order> searchAndFilterOrders(OrderFilterRequestDto orderFilterRequestDto, Sort.Direction direction, int pageNumber, int pageSize, String sortBy) {
        return repository.searchAndFilterOrders(orderFilterRequestDto, direction, pageNumber, pageSize, sortBy);
    }

    @Override
    @Transactional
    public List<Order> findAllByClientIdAndOrderStatus(Long clientId, OrderStatus orderStatus) {
        return repository.findAllByClientIdAndOrderStatus(clientId, orderStatus);
    }

    @Override
    @Transactional
    public List<Order> findAllByTechnicianIdAndOrderStatus(Long technicianId, OrderStatus orderStatus) {
        return repository.findAllByTechnicianIdAndOrderStatus(technicianId, orderStatus);
    }
}