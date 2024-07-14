package org.example.cosmeticwebpro.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cosmeticwebpro.domains.Cart;
import org.example.cosmeticwebpro.domains.User;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDTO {
  private User user;
  private Cart cart;
}
