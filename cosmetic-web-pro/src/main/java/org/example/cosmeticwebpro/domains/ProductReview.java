package org.example.cosmeticwebpro.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_review")
public class ProductReview {
  @Id
  @SequenceGenerator(
      name = "product_history_seq",
      sequenceName = "product_history_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_history_seq")
  @Column(name = "id")
  private Long id;

  @Column(name = "content")
  private String content;

  @Column(name = "product_rating")
  private Integer productRating;

  @Column(name = "shop_rating")
  private Integer shopRating;

  @Column(name = "delivery_speed")
  private String deliverySpeed;

  @Column(name = "created_date")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createdDate;

  @Column(name = "product_id")
  private Long ProductId;

  @Column(name = "invoice_id")
  private Long InvoiceId;
}
