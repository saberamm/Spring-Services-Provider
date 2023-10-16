package com.example.servicesprovider.model;

import com.example.servicesprovider.base.model.BaseModel;
import com.example.servicesprovider.model.enumeration.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
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

    @NotNull(message = "Order price can not be null")
    private Double orderPrice;

    private String orderDescription;

    @FutureOrPresent
    @NotNull(message = "Work time can not be null")
    private LocalDateTime workTime;

    @NotNull(message = "Order address can not be null")
    private String orderAddress;

    @NotNull(message = "Suggestion price can not be null")
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    private List<Offer> offerList;

    @ManyToOne(cascade = CascadeType.ALL)
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL)
    private SubService subService;
}