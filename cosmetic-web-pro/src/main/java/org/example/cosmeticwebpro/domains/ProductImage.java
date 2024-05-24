package org.example.cosmeticwebpro.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_image")
public class ProductImage {
    @Id
    @SequenceGenerator(
            name = "product_image_seq",
            sequenceName = "product_image_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_image_seq")
    @Column(name = "id")
    private Long id;

    private String name;
    private String imageId;

    @Column(name = "url_image")
    private String imageUrl;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product products;

    public ProductImage(String name, String imageUrl, String imageId) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.imageId = imageId;
    }
}
