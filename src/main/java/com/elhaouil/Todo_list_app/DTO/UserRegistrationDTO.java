package com.elhaouil.Todo_list_app.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
    @NotBlank(message = "username is mandatory")
    @NotEmpty(message = "username is mandatory")
    private String username;

    @NotBlank(message = "email is mandatory")
    @NotEmpty(message = "email is mandatory")
    @Email(message = "Email is not formatted correctly")
    private String email;

    @NotBlank(message = "password is mandatory")
    @Size(min = 8, message = "the password is too short")
    private String password;
}
