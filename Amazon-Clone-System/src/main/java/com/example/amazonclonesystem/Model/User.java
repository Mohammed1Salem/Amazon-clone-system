package com.example.amazonclonesystem.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    @NotEmpty(message = "id must be filled")
    private String id;
    @NotEmpty(message = "name must be filled")
    @Size(min = 6, message = "name must be more than 5 length")
    private String username;
    @NotEmpty
    @Pattern(regexp = "^(?=.*?[a-zA-Z])(?=.*?[0-9]).{6,}$", message = "password must be more than 5 digits,only characters and numbers")
    private String password;
    @Email
    private String email;
    @Pattern(regexp = "^(Admin|Customer)$", message = "role must (Admin, User) only")
    private String role;
    @NotNull
    @PositiveOrZero
    private double balance;
}
