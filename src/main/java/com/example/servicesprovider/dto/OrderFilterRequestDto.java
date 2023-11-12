package com.example.servicesprovider.dto;

import com.example.servicesprovider.model.enumeration.OrderStatus;

import java.time.LocalDateTime;

public class OrderFilterRequestDto {

    private Long clientId;

    private Long technicianId;

    private LocalDateTime orderDateStart;

    private LocalDateTime orderDateEnd;

    private OrderStatus orderStatus;

    private String subServiceName;

    private String generalServiceName;
}
