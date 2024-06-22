package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.domains.Category;
import org.example.cosmeticwebpro.exceptions.CosmeticException;

public interface CategoryService {
  Category getById(Long categoryId) throws CosmeticException;

  Category create(String categoryName) throws CosmeticException;

  Category update(Category category) throws CosmeticException;

  void delete(Long categoryId) throws CosmeticException;

  List<Category> getAll() throws CosmeticException;
}
