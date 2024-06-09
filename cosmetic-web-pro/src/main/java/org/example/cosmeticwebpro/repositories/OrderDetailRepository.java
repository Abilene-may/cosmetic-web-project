package org.example.cosmeticwebpro.repositories;

import org.example.cosmeticwebpro.domains.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>,
    JpaSpecificationExecutor<Long> {

}
