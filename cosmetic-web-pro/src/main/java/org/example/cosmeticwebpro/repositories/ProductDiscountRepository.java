package org.example.cosmeticwebpro.repositories;

import org.example.cosmeticwebpro.domains.ProductDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductDiscountRepository extends JpaRepository<ProductDiscount, Long>,
    JpaSpecificationExecutor<Long> {

}
