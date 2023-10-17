package com.example.servicesprovider.model;

import com.example.servicesprovider.base.model.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
public class Offer extends BaseModel<Long> {

    @NotNull(message = "Time offer sent can not be null")
    private LocalDateTime timeOfferSent;

    @NotNull(message = "Time for start working can not be null")
    private LocalDateTime timeForStartWorking;

    @NotNull(message = "Suggestion price can not be null")
    private Double suggestionPrice;

    @NotNull(message = "Time for end working can not be null")
    private LocalDateTime timeForEndWorking;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Technician technician;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Order order;
}
