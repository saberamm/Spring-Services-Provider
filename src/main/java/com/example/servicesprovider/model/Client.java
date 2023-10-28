package com.example.servicesprovider.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
    private String phoneNumber;

    @Column(name = "nationalCode", unique = true)
    private String nationalCode;

    @OneToMany(mappedBy = "client")
    private List<Order> orderList;
}
