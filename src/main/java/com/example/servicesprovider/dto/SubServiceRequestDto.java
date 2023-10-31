package com.example.servicesprovider.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubServiceRequestDto {

    private Long id;

    @NotNull(message = "SubService name can not be null")
    private String subServiceName;

    @NotNull(message = "Base price can not be null")
    private Double basePrice;

    @NotNull(message = "Description can not be null")
    private String description;

    private Long generalServiceId;
}
