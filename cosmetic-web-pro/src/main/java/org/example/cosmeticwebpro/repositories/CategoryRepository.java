package org.example.cosmeticwebpro.repositories;

import org.example.cosmeticwebpro.domains.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<Category, Long>,
    JpaSpecificationExecutor<Long> {

}
