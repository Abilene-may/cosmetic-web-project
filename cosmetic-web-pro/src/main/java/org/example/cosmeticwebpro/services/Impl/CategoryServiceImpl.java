package org.example.cosmeticwebpro.services.Impl;

import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.domains.Category;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.repositories.CategoryRepository;
import org.example.cosmeticwebpro.services.CategoryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  public Category getById(Long catedoryId) throws CosmeticException {
    var category = categoryRepository.findById(catedoryId);
    if (category.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.CATEGORY_NOT_FOUND,
          ExceptionUtils.messages.get(ExceptionUtils.CATEGORY_NOT_FOUND));
    }
    return category.get();
  }
}
