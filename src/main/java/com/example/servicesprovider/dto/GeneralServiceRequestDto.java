package com.example.servicesprovider.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeneralServiceRequestDto {

    private Long id;

    @NotNull(message = "Service name can not be null")
    private String serviceName;
}
