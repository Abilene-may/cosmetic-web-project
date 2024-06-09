package org.example.cosmeticwebpro.models.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressReqDTO {
  private String fullName;

  private String phoneNumber;

  private String provinceName;

  private String districtName;

  private String wardName;

  private String addressDetail;

  private Long userId;
}
