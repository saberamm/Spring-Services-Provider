package com.example.servicesprovider.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ViewPointResponseDto {

    private Long id;

    private String comment;

    private Double score;

    private TechnicianResponseDto technicianResponseDto;
}
