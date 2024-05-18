package org.example.cosmeticwebpro.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpReqDTO {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
