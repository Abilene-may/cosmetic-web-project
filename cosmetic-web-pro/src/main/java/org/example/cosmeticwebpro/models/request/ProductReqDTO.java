package org.example.cosmeticwebpro.models.request;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReqDTO {
    private String title;

    private String description;

    private String currentCost;

    private String category;

    private String madeIn;

    private Integer capacity;

    private Integer quantity;

    private Long brandId;
}
