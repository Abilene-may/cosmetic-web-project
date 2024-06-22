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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "cart_line")
public class CartLine {
  @Id
  @SequenceGenerator(
      name = "cart_line_seq",
      sequenceName = "cart_line_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_line_seq")
  @Column(name = "id")
  private Long id;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "created_date")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createdDate;

  @Column(name = "modified_date")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime modifiedDate;

  @Column(name = "cart_id")
  private Long cartId;

  @Column(name = "product_id")
  private Long productId;
}
