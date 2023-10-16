package com.example.servicesprovider.model;

import com.example.servicesprovider.base.model.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
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

    @ManyToOne(cascade = CascadeType.ALL)
    private Technician technician;

    @ManyToOne(cascade = CascadeType.ALL)
    private Order order;
}
