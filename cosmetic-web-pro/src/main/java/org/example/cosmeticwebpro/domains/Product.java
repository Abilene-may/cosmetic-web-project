package org.example.cosmeticwebpro.domains;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @SequenceGenerator(
            name = "product_seq",
            sequenceName = "product_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "current_cost")
    private Integer currentCost;

    @Column(name = "made_in")
    private String madeIn;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "product_status")
    private String productStatus;

    @Column(name = "count_view")
    private Integer countView;

    @Column(name = "count_purchase")
    private Integer countPurchase;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedDate;

    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "discount_id")
    private Long disCountId;

    @Column(name = "category_id")
    private Long categoryId;

    @OneToMany(mappedBy = "product")
    private Set<ProductDiscount> productDiscounts = new HashSet<>();
}
