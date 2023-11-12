package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.dto.OrderFilterRequestDto;
import com.example.servicesprovider.model.Order;
import com.example.servicesprovider.model.SubService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface OrderService extends BaseService<Order, Long> {
    List<Order> findBySubService(SubService subService);

    @Transactional
    Page<Order> searchAndFilterUsers(OrderFilterRequestDto orderFilterRequestDto, Sort.Direction direction, int pageNumber, int pageSize, String sortBy);
}