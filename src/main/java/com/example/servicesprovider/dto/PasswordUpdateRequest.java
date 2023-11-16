package com.example.servicesprovider.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateRequest {

    String userName;

    @NotNull(message = "oldPassword can not be null")
    String oldPassword;

    @NotNull(message = "newPassword can not be null")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,50}$",
            message = "Password must contain 1 digit, 1 lowercase letter, 1 uppercase letter,1 special character, And at least 8 characters.")
    String newPassword;

    @NotNull(message = "duplicateNewPassword can not be null")
    String duplicateNewPassword;
}
