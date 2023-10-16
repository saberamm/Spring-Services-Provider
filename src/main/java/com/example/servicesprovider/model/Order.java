package com.example.servicesprovider.model;

import com.example.servicesprovider.base.model.BaseModel;
import com.example.servicesprovider.model.enumeration.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table(name = "order_table")
public class Order extends BaseModel<Long> {

    @NotNull
    private Double orderPrice;

    private String orderDescription;

    @FutureOrPresent
    private LocalDateTime workTime;

    @NotNull
    private String orderAddress;

    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    private List<Offer> offerList;

    @ManyToOne(cascade = CascadeType.ALL)
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL)
    private SubService subService;
}