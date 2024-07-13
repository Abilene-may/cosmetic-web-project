package org.example.cosmeticwebpro.repositories;

import org.example.cosmeticwebpro.domains.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Long> {

  @Query( value = " select name from brand where id = :brandId "
      , nativeQuery = true)
  String findBrandNameById(Long brandId);
}
