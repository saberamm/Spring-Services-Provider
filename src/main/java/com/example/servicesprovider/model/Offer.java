package com.example.servicesprovider.model;

import com.example.servicesprovider.base.model.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
public class Offer extends BaseModel<Long> {

    private LocalDateTime timeOfferSent;

    private LocalDateTime timeForStartWorking;

    private Double suggestionPrice;

    private LocalDateTime timeForEndWorking;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Technician technician;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Order order;
}
