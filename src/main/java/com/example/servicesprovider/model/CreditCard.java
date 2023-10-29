package com.example.servicesprovider.model;

import com.example.servicesprovider.base.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
public class CreditCard extends BaseModel<Long> {

    @Column(unique = true)
    private String creditCardNumber;

    private LocalDate expireDate;

    private String cvv2;

    private String secondPassword;

}