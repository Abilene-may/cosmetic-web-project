package org.example.cosmeticwebpro.repositories;

import java.util.Optional;
import org.example.cosmeticwebpro.domains.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Long> {

    @Query(value = " select * from cart where user_id = :userId "
    , nativeQuery = true)
    Optional<Cart> findByUserId(Long userId);
}
