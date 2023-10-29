package com.example.servicesprovider.dto;

import com.example.servicesprovider.model.enumeration.OrderStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderRequestDto {

    private Long id;

    @NotNull(message = "Order price can not be null")
    private Double orderPrice;

    private String orderDescription;

    @FutureOrPresent(message = "Work time cant be in past")
    @NotNull(message = "Work time can not be null")
    private LocalDateTime workTime;

    @NotNull(message = "Order address can not be null")
    private String orderAddress;

    private Long selectedOffersId;

    @NotNull(message = "Suggestion price can not be null")
    private OrderStatus orderStatus;
}
