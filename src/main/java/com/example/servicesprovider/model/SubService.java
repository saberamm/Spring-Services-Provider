package com.example.servicesprovider.model;

import com.example.servicesprovider.base.model.BaseModel;
import jakarta.persistence.*;
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
public class SubService extends BaseModel<Long> {

    @Column(unique = true)
    private String subServiceName;

    private Double basePrice;

    private String description;

    @OneToMany(mappedBy = "subService")
    private List<SubServiceTechnician> subServiceTechnicianList;

    @OneToMany(mappedBy = "subService")
    private List<Order> orderList;

    @ManyToOne(cascade = CascadeType.MERGE)
    private GeneralService generalService;
}
