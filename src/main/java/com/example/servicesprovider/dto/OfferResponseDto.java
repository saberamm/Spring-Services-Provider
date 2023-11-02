package com.example.servicesprovider.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OfferResponseDto {

    private Long id;

    private LocalDateTime timeForStartWorking;

    private Double suggestionPrice;

    private LocalDateTime timeOfferSent;

    private LocalDateTime timeForEndWorking;

    private TechnicianResponseDto technicianResponseDto;

    private OrderResponseDto orderResponseDto;
}
