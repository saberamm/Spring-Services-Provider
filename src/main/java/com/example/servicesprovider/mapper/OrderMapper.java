package com.example.servicesprovider.mapper;

import com.example.servicesprovider.dto.OrderRequestDto;
import com.example.servicesprovider.dto.OrderResponseDto;
import com.example.servicesprovider.model.Order;

public interface OrderMapper {
    Order map(OrderRequestDto orderRequestDto);

    OrderResponseDto map(Order order);

    void map(OrderRequestDto orderRequestDto, Order order);
}
