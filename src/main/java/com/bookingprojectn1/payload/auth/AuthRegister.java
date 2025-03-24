package com.bookingprojectn1.payload.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRegister {

    @Size(min = 3, max = 25)
    @NotBlank
    private String firstName;

    @Size(min = 3, max = 25)
    @NotBlank
    private String lastName;

    @Pattern(regexp = "^998([0-9][012345789]|[0-9][125679]|7[01234569])[0-9]{7}$",
            message = "Invalid Uzbekistan phone number")
    private String phoneNumber;

    @Size(min = 5, max = 12)
    private String password;
}
