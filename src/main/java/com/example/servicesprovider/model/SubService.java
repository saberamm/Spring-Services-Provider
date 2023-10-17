package com.example.servicesprovider.model;

import com.example.servicesprovider.base.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
public class SubService extends BaseModel<Long> {

    @NotNull(message = "SubService name can not be null")
    @Column(unique = true)
    private String subServiceName;

    @NotNull(message = "Base price can not be null")
    private Double basePrice;

    @NotNull(message = "Description can not be null")
    private String description;

    @ManyToMany(mappedBy = "subServiceList")
    private List<Technician> technicianList;

    @OneToMany(mappedBy = "subService")
    private List<Order> orderList;

    @ManyToOne(cascade = CascadeType.MERGE)
    private GeneralService generalService;
}
