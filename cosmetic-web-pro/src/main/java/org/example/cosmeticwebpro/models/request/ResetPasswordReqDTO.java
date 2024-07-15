package org.example.cosmeticwebpro.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordReqDTO {
  private Long userId;
  private String newPassword;
  private String reconfirmPassword;
}
