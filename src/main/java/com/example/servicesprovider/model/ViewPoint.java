package com.example.servicesprovider.model;

import com.example.servicesprovider.base.model.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
public class ViewPoint extends BaseModel<Long> {

    private String comment;

    @Min(value = -10, message = "Score must be between -10 to 10")
    @Max(value = 10, message = "Score must be between -10 to 10")
    private Integer score;

    @ManyToOne(cascade = CascadeType.ALL)
    private Technician technician;
}