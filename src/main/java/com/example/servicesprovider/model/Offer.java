package com.example.servicesprovider.model;

import com.example.servicesprovider.base.model.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
public class Offer extends BaseModel<Long> {

    private LocalDateTime timeOfferSent;

    private LocalDateTime timeForStartWorking;

    private Double suggestionPrice;

    private LocalDateTime timeForEndWorking;

    @ManyToOne(cascade = CascadeType.ALL)
    private Technician technician;

    @ManyToOne(cascade = CascadeType.ALL)
    private Order order;
}
