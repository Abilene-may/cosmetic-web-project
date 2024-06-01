package org.example.cosmeticwebpro.models.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartReqDTO {
  private Long productId;

  private Long cartId;

  private Integer quantity;
}
