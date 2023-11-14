package com.example.servicesprovider.model;

import com.example.servicesprovider.base.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "user_table")
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public class User extends BaseModel<Long> {

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String userName;

    private String password;

    private LocalDate birthDate;

    private final LocalDateTime signUpDate = LocalDateTime.now();

    @Column(unique = true)
    private String email;

    @Column(insertable = false, updatable = false)
    private String role;

    @Column(unique = true)
    private String confirmationToken;

    private boolean confirmed;
}
