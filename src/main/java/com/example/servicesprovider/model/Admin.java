package com.example.servicesprovider.model;

import com.example.servicesprovider.model.enumeration.AdminPosition;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
public class Admin extends User {

    @Enumerated(EnumType.STRING)
    private AdminPosition position;
}