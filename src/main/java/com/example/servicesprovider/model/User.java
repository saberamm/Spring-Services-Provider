package com.example.servicesprovider.model;

import com.example.servicesprovider.base.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.*;

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
public class User extends BaseModel<Long> {

    @NotNull(message = "Name can not be null")
    private String firstName;

    @NotNull(message = "LastName can not be null")
    private String lastName;

    @NotNull(message = "userName can not be null")
    @Column(unique = true)
    private String userName;

    @NotNull(message = "Password cannot be null")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,50}$",
//            message = "Password must contain 1 digit, 1 lowercase letter, 1 uppercase letter,1 special character, And at least 8 characters.")
    private String password;

    @NotNull(message = "BirthDate cannot be null")
    private LocalDate birthDate;

    private final LocalDateTime signUpDate = LocalDateTime.now();

    @Column(unique = true)
    @NotNull(message = "Email cannot be null")
    @Email
    private String email;
}
