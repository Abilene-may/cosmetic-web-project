package org.example.cosmeticwebpro.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenAuthDTO {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
}
