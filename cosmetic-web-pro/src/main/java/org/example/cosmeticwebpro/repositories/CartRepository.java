package org.example.cosmeticwebpro.repositories;

import org.example.cosmeticwebpro.domains.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CartRepository extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Long> {
    Cart findByUserId(Long userId);
}
