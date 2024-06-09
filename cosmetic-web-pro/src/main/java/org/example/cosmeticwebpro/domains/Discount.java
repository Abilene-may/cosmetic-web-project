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
import java.util.Date;
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

  @Column(name = "discount")
  private Integer discount;

  @Column(name = "from_date")
  private Date fromDate;

  @Column(name = "to_date")
  private Date toDate;

  @Column(name = "start_hour")
  private Integer start_hour;

  @Column(name = "start_minute")
  private Integer startMinute;

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

  @Column(name = "apply_to")
  private String applyTo;

  @Column(name = "min_amount")
  private Integer minAmount;
}
