package org.example.cosmeticwebpro.domains;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "order_detail")
public class OrderDetail {
  @Id
  @SequenceGenerator(
      name = "order_detail_seq",
      sequenceName = "order_detail_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_detail_seq")
  @Column(name = "id")
  private Long id;

  @NonNull
  @Column(name = "product_title")
  private String productTitle;

  @Column(name = "product_image_url")
  private String productImageUrl;

  @NonNull
  @Column(name = "product_cost")
  private Integer productCost;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "discount_product")
  private Integer discountProduct;

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "order_id")
  private Long orderId;
}
