package com.example.servicesprovider.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
public class SubServiceTechnician {

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    private Technician technician;

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    private SubService subService;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class SubServiceTechnicianId implements Serializable {
        private Technician technician;
        private SubService subService;
    }
}
