package com.example.servicesprovider.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
public class Client extends User {

    private Double clientCredit;

    @Column(name = "phoneNumber")
    @NotNull(message = "PhoneNumber can not be null")
    private String phoneNumber;

    @Column(name = "nationalCode", unique = true)
    @NotNull(message = "NationalCode can not be null")
    @Size(min = 10, max = 10, message = "NationalCode  must have 10 digits")
    private String nationalCode;

    @OneToMany(mappedBy = "client")
    private List<Order> orderList;
}
