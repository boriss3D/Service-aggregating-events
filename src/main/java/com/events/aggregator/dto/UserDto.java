package com.events.aggregator.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UserDto {

    @NotBlank(message = "User name should not be empty")
    @Length(max = 50, message = "Name maximum length is 50 characters")
    private String name;

    @NotBlank(message = "Email should not be empty")
    @Email
    private String email;

    @Size(min = 8, max = 30, message = "Password should be 8-30 characters long")
    private String password;

    @Size(min = 8, max = 30, message = "Password should be 8-30 characters long")
    private String password2;

    private String role;
}
