package org.example.cosmeticwebpro.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_history")
public class ProductHistory {
  @Id
  @SequenceGenerator(
      name = "product_history_seq",
      sequenceName = "product_history_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_history_seq")
  @Column(name = "id")
  private Long id;

  @Column(name = "title")
  private String title;

  @Lob
  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "old_cost")
  private String oldCost;

  @Column(name = "category")
  private String category;

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

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "discount_id")
  private Long discountId;
}
