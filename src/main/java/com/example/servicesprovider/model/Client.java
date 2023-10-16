package com.example.servicesprovider.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
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
