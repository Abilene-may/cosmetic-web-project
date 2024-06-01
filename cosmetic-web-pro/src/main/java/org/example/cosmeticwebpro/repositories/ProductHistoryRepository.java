package org.example.cosmeticwebpro.repositories;

import org.example.cosmeticwebpro.domains.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long>,
    JpaSpecificationExecutor<Long> {

}
