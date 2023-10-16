package com.example.servicesprovider.model;

import com.example.servicesprovider.base.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class GeneralService extends BaseModel<Long> {

    @Column(unique = true)
    @NotNull(message = "Service name can not be null")
    private String serviceName;

    @OneToMany(mappedBy = "generalService")
    private List<SubService> subServiceList;
}
