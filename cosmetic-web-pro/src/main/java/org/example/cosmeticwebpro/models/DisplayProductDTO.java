package org.example.cosmeticwebpro.models;

import java.time.LocalDateTime;

public interface DisplayProductDTO {
  Long getId();
  String getTitle();
  String getDescription();
  Double getCurrentCost();
  String getMadeIn();
  Integer getCapacity();
  Integer getQuantity();
  String getProductStatus();
  Integer getCountView();
  Integer getCountPurchase();
  LocalDateTime getCreatedDate();
  LocalDateTime getModifiedDate();
  Long getBrandId();
  Long getCategoryId();
  String getImageUrl();
  Long getDiscountId();
  Integer getPercentDiscount();
  String getCategoryName();
  String getBrandName();
  Integer getTotalRating();
  Double getAverageRating();
}
