package org.example.cosmeticwebpro.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cosmeticwebpro.domains.Cart;
import org.example.cosmeticwebpro.domains.User;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenAuthDTO {
    private String accessToken;

    private String refreshToken;

    private String tokenType = "Bearer";

    private User user;

    private Cart cart;
}
