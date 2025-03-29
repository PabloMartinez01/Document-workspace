package com.pablodev.documentworkspace.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NotBlank
    @Size(min =  3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 8)
    private String password;
}
