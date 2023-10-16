package com.example.servicesprovider.model;

import com.example.servicesprovider.base.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
public class SubService extends BaseModel<Long> {

    @NotNull
    @Column(unique = true)
    private String subServiceName;

    @NotNull
    private Double basePrice;

    @NotNull
    private String description;

    @ManyToMany
    @JoinTable(
            name = "technician_subservice",
            joinColumns = @JoinColumn(name = "subservice_id"),
            inverseJoinColumns = @JoinColumn(name = "technician_id")
    )
    private List<Technician> technicianList;

    @OneToMany(mappedBy = "subService")
    private List<Order> orderList;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private GeneralService generalService;
}
