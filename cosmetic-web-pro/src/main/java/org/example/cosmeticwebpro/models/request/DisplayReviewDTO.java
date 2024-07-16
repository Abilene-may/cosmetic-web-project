package org.example.cosmeticwebpro.models.request;

import java.time.LocalDateTime;

public interface DisplayReviewDTO {
    Long getId();
    String getContent();
    Integer getProductRating();
    LocalDateTime getCreatedDate();
    Long getProductId();
    Long getOrderId();
    String getUsername();
}
