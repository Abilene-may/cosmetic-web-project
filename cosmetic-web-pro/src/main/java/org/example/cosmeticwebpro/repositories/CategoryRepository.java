package org.example.cosmeticwebpro.repositories;

import java.util.List;
import org.example.cosmeticwebpro.domains.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long>,
    JpaSpecificationExecutor<Long> {

  @Query( value = " select category_name from category where id = :categoryId "
  , nativeQuery = true)
  String findCategoryNameById(Long categoryId);

  @Query( value = " select * from category order by created_date desc "
      , nativeQuery = true)
  List<Category> findAllCategories();
}
