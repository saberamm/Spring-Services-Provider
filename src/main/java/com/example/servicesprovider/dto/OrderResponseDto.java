package com.example.servicesprovider.dto;

import com.example.servicesprovider.model.enumeration.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDto {

    private Long id;

    private Double orderPrice;

    private String orderDescription;

    private LocalDateTime workTime;

    private String orderAddress;

    private Long selectedOffersId;

    private OrderStatus orderStatus;
}
