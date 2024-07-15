package com.sandbox.PhotoAppApiUsers.users.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestModel {

    @Size(min = 2, message = "First name must have more than one character!")
    @NotNull(message = "First name cant be null!!!")
    private String firstName;
    @Size(min = 2, message = "Last name must have more than one character!")
    @NotNull(message = "Last name cant be null!!!")
    private String lastName;
    @NotNull(message = "Password cant be null!")
    @Size(min=8, max=16, message = "Password must have minimum 8 and up to 16 characters")
    private String password;
    @NotNull(message = "Email cannot be null!")
    @Email
    private String email;

}
