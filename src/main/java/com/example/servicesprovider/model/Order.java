package com.example.servicesprovider.model;

import com.example.servicesprovider.base.model.BaseModel;
import com.example.servicesprovider.model.enumeration.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "order_table")
public class Order extends BaseModel<Long> {

    private Double orderPrice;

    private String orderDescription;

    private LocalDateTime workTime;

    private String orderAddress;

    private Long selectedOffersId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<Offer> offerList;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Client client;

    @ManyToOne(cascade = CascadeType.MERGE)
    private SubService subService;
}