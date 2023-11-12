package com.example.servicesprovider.dto;

import com.example.servicesprovider.model.enumeration.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderFilterRequestDto {

    private Long clientId;

    private Long technicianId;

    private LocalDateTime orderDateStart;

    private LocalDateTime orderDateEnd;

    private OrderStatus orderStatus;

    private String subServiceName;

    private String generalServiceName;
}
