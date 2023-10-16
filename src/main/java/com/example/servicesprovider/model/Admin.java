package com.example.servicesprovider.model;

import com.example.servicesprovider.model.enumeration.AdminPosition;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
public class Admin extends User {

    @NotNull(message = "Position can not be null")
    @Enumerated(EnumType.STRING)
    private AdminPosition position;
}