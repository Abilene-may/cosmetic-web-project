package org.example.cosmeticwebpro.domains;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @SequenceGenerator(
            name = "cart_seq",
            sequenceName = "cart_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_seq")
    @Column(name = "id")
    private Long id;

    @Column(name="total_quantity")
    private Integer totalQuantity;

    @Column(name="created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @Column(name = "user_id")
    private Long userId;

}
