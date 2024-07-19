package org.example.cosmeticwebpro.models.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserReqDTO {
  private String email;
  private String firstName;
  private String lastName;
  private String userName;
  private String password;
  private Long roleId;
}
