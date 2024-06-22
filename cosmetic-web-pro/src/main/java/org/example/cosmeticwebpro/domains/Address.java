package org.example.cosmeticwebpro.domains;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Entity
@Table(name="address")
public class Address {
    @Id
    @SequenceGenerator(
            name = "address_seq",
            sequenceName = "address_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "full_name")
    private String fullName;

    @NonNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @NonNull
    @Column(name = "province_name")
    private String provinceName;

    @NonNull
    @Column(name = "district_name")
    private String districtName;

    @NonNull
    @Column(name = "ward_name")
    private String wardName;

    @Column(name = "address_detail")
    private String addressDetail;

    @NonNull
    @Column(name = "user_id")
    private Long userId;
}
