package org.example.cosmeticwebpro.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginReqDTO {
    private String usernameOrEmail;
    private String password;
}
