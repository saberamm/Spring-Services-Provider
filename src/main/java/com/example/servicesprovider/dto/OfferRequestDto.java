package com.example.servicesprovider.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OfferRequestDto {

    private Long id;

    @NotNull(message = "Time for start working can not be null")
    @FutureOrPresent(message = "time for start working cant be in past")
    private LocalDateTime timeForStartWorking;

    @NotNull(message = "Suggestion price can not be null")
    private Double suggestionPrice;

    @NotNull(message = "Time for end working can not be null")
    private LocalDateTime timeForEndWorking;
}
