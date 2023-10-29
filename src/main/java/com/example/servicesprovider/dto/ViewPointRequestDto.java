package com.example.servicesprovider.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ViewPointRequestDto {

    private Long id;

    private String comment;

    @Min(value = -10, message = "Score must be between -10 to 10")
    @Max(value = 10, message = "Score must be between -10 to 10")
    @NotNull
    private Integer score;
}
