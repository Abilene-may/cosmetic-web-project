package org.example.cosmeticwebpro.services;

import org.example.cosmeticwebpro.domains.Category;
import org.example.cosmeticwebpro.exceptions.CosmeticException;

public interface CategoryService {
  Category getById(Long catedoryId) throws CosmeticException;
}
