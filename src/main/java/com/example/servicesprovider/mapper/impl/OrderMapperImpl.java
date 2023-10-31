package com.example.servicesprovider.mapper.impl;

import com.example.servicesprovider.dto.*;
import com.example.servicesprovider.mapper.OrderMapper;
import com.example.servicesprovider.model.Order;
import com.example.servicesprovider.service.ClientService;
import com.example.servicesprovider.service.SubService_Service;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderMapperImpl implements OrderMapper {
    ClientService clientService;
    SubService_Service subService_service;
    private final ModelMapper modelMapper;

    @Override
    public Order map(OrderRequestDto orderRequestDto) {
        if (orderRequestDto == null) return null;
        Order order = modelMapper.map(orderRequestDto, Order.class);
        order.setClient(clientService.findById(orderRequestDto.getClientId()));
        order.setSubService(subService_service.findById(orderRequestDto.getSubServiceId()));
        return order;
    }

    @Override
    public OrderResponseDto map(Order order) {
        if (order == null) return null;
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        orderResponseDto.setClientResponseDto(modelMapper.map(order.getClient(), ClientResponseDto.class));
        orderResponseDto.setSubServiceResponseDto(modelMapper.map(order.getSubService(), SubServiceResponseDto.class));
        return orderResponseDto;
    }

    @Override
    public void map(OrderRequestDto orderRequestDto, Order order) {
        modelMapper.map(orderRequestDto, order);

        if (orderRequestDto.getClientId() != null) {
            order.setClient(clientService.findById(orderRequestDto.getClientId()));
        }

        if (orderRequestDto.getSubServiceId() != null) {
            order.setSubService(subService_service.findById(orderRequestDto.getSubServiceId()));
        }
    }
}
