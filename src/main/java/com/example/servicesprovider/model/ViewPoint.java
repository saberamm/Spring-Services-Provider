package com.example.servicesprovider.model;

import com.example.servicesprovider.base.model.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
public class ViewPoint extends BaseModel<Long> {

    private String comment;

    private Integer score;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Technician technician;
}