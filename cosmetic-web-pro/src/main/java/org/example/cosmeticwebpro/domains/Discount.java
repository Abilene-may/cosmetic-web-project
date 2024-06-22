package org.example.cosmeticwebpro.domains;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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
@Table(name = "discount")
public class Discount {
  @Id
  @SequenceGenerator(
      name = "discount_seq",
      sequenceName = "discount_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discount_seq")
  @Column(name = "id")
  private Long id;

  @NonNull
  @Column(name = "discount_percent")
  private Integer discountPercent;

  @NonNull
  @Column(name = "from_date")
  private Date fromDate;

  @NonNull
  @Column(name = "to_date")
  private Date toDate;

  @NonNull
  @Column(name = "start_hour")
  private Integer start_hour;

  @NonNull
  @Column(name = "start_minute")
  private Integer startMinute;

  @NonNull
  @Column(name = "end_hour")
  private Integer endHour;

  @Column(name = "end_minute")
  private Integer endMinute;

  @Column(name = "created_date")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createdDate;

  @Column(name = "modified_date")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime modifiedDate;

  @NonNull
  @Column(name = "apply_to")
  private String applyTo;

  @Column(name = "min_amount")
  private Integer minAmount;

  @Column(name = "max_usage")
  private Integer maxUsage;

  @Column(name = "total_usage")
  private Integer totalUsage;

  @OneToMany(mappedBy = "discount")
  private Set<ProductDiscount> productDiscounts = new HashSet<>();
}
