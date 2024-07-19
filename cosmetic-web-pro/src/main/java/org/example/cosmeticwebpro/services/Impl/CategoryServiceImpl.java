package org.example.cosmeticwebpro.services.Impl;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.domains.Category;
import org.example.cosmeticwebpro.domains.Product;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.repositories.CategoryRepository;
import org.example.cosmeticwebpro.repositories.ProductRepository;
import org.example.cosmeticwebpro.services.CategoryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;

  // view detail a category
  @Override
  public Category getById(Long categoryId) throws CosmeticException {
    var category = categoryRepository.findById(categoryId);
    if (category.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.CATEGORY_NOT_FOUND,
          ExceptionUtils.messages.get(ExceptionUtils.CATEGORY_NOT_FOUND));
    }
    return category.get();
  }

  // create a new category
  @Override
  public Category create(String categoryName) throws CosmeticException {
    LocalDateTime today = LocalDateTime.now();
    this.checkNotNull(categoryName);
    Category category =
        Category.builder()
            .categoryName(categoryName)
            .createdDate(today)
            .modifiedDate(today)
            .build();
    categoryRepository.save(category);
    return category;
  }

  // update a category
  @Transactional
  @Override
  public Category update(Category category) throws CosmeticException {
    // check category exist
    var updateCategory = this.getById(category.getId());
    this.checkNotNull(category.getCategoryName());
    LocalDateTime today = LocalDateTime.now();
    updateCategory.setCategoryName(category.getCategoryName());
    updateCategory.setModifiedDate(today);
    categoryRepository.save(updateCategory);
    return updateCategory;
  }

  private void checkNotNull(String name) throws CosmeticException {
    if (name.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.CATEGORY_NAME_NOT_EMPTY,
          ExceptionUtils.messages.get(ExceptionUtils.CATEGORY_NAME_NOT_EMPTY));
    }
  }

  // Delete a category
  @Override
  public void delete(Long categoryId) throws CosmeticException {
    // Get a list of products in this category
    List<Product> products = productRepository.findAllByCategoryId(categoryId);

    // remove category_id information from products
    for (Product product : products) {
      product.setCategoryId(null);
      productRepository.save(product);
    }
    var category = this.getById(categoryId);
    categoryRepository.delete(category);
  }

  @Override
  public List<Category> getAll() throws CosmeticException {
    return categoryRepository.findAllCategories();
  }
}
