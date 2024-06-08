package org.example.cosmeticwebpro.models.request;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductReqDTO {
    private String title;

    private String description;

    private Integer currentCost;

    private String madeIn;

    private Integer capacity;

    private Integer quantity;

    private String productStatus;

    private Long brandId;

    private Long discountId;

    private Long categoryId;
}
