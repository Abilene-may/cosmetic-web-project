package org.example.cosmeticwebpro.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_discount")
public class ProductDiscount {
  @Id
  @SequenceGenerator(
      name = "product_discount_seq",
      sequenceName = "product_discount_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_discount_seq")
  @Column(name = "id")
  private Long id;

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "discount_id")
  private Long discountId;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "product_id", insertable = false, updatable = false)
  private Product product;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "discount_id", insertable = false, updatable = false)
  private Discount discount;
}
