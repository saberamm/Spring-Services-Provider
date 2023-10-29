package com.example.servicesprovider.model;

import com.example.servicesprovider.model.enumeration.TechnicianStatus;
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
public class Technician extends User {

    @Enumerated(EnumType.STRING)
    private TechnicianStatus technicianStatus;

    private byte[] technicianPhoto;

    private Double technicianCredit;

    private Double negativeScore;

    private Double overallScore;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "nationalCode", unique = true)
    private String nationalCode;

    private String aboutMe;

    @OneToMany(mappedBy = "technician", fetch = FetchType.EAGER)
    private List<ViewPoint> viewPointList;

    @OneToMany(mappedBy = "technician")
    private List<SubServiceTechnician> subServiceTechnicianList;

    @OneToMany(mappedBy = "technician")
    private List<Offer> offerList;

}