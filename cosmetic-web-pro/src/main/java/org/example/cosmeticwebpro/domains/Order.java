package org.example.cosmeticwebpro.domains;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
  @Id
  @SequenceGenerator(name = "orders_seq", sequenceName = "orders_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
  @Column(name = "id")
  private Long id;

  @Column(name = "full_name")
  private String fullName;

  @NonNull
  @Column(name = "phone_number")
  private String phoneNumber;

  @NonNull
  @Column(name = "address_detail")
  private String addressDetail;

  @Column(name = "total_quantity")
  private Integer totalItem;

  @Column(name = "total_cost")
  private double totalCost;

  @Column(name = "discount_order")
  private Integer discountOrder;

  @Column(name = "shipping_cost")
  private double shippingCost;

  @Column(name = "note")
  private String note;

  @Column(name = "status")
  private String status;

  @Column(name = "payment_method")
  private String paymentMethod;

  @Column(name = "order_date")
  private LocalDateTime orderDate;

  @Column(name = "shipping_date")
  private LocalDateTime shippingDate;

  @Column(name = "complition_date")
  private LocalDateTime complitionDate;

  @Column(name = "user_id")
  private Long userId;
  
}
