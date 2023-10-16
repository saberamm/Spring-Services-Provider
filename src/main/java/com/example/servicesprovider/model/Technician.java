package com.example.servicesprovider.model;

import com.example.servicesprovider.model.enumeration.TechnicianStatus;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
public class Technician extends User {

    @NotNull
    @Enumerated(EnumType.STRING)
    private TechnicianStatus technicianStatus;

    private byte[] technicianPhoto;

    private Double technicianCredit;

    @Column(name = "phoneNumber")
    @NotNull
    @Size(min = 11, max = 11, message = "phoneNumber  must have 11 digits")
    private String phoneNumber;

    @Column(name = "nationalCode",unique = true)
    @NotNull(message = "nationalCode cannot be null")
    @Size(min = 10, max = 10, message = "national code  must have 10 digits")
    private String nationalCode;

    @Size(min = 1, max = 200, message = "about Me must be between 1 to 200")
    private String aboutMe;

    @OneToMany(mappedBy = "technician")
    private List<ViewPoint> viewPointList;

    @ManyToMany(mappedBy = "technicianList")
    private List<SubService> subServiceList;

    @OneToMany(mappedBy = "technician")
    private List<Offer> offerList;
}