package org.example.cosmeticwebpro.models;

import java.time.LocalDateTime;

public interface UserDTO {
  Long getId();
  String getEmail();
  String getFirstName();
  String getLastName();
  String getUserName();
  String getPassword();
  LocalDateTime getCreatedDate();
  LocalDateTime getModifiedDate();
  String getAccountStatus();
  LocalDateTime getRequestDate();
  String getRoleName();
}
