package com.example.servicesprovider.model;

import com.example.servicesprovider.base.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
public class CreditCard extends BaseModel<Long> {

    @NotNull(message = "creditCardNumber cannot be null")
    @Size(min = 16, max = 16, message = "creditCardNumber must be 16 characters")
    @Column(unique = true)
    private String creditCardNumber;

    @NotNull(message = "expire date cannot be null")
    private LocalDate expireDate;

    @Size(min = 4, max = 4, message = "cvv2 must be 4 characters")
    @NotNull(message = "cvv2 cannot be null")
    private String cvv2;

    @Size(min = 8, max = 8, message = "secondPassword must be 8 characters")
    @NotNull(message = "secondPassword cannot be null")
    private String secondPassword;

}