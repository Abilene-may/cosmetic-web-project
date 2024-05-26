package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.domains.Brand;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.request.BrandReqDTO;

public interface BrandService {
  Brand create(BrandReqDTO brandReqDTO) throws CosmeticException;

  void update(Brand updateBrand) throws CosmeticException;

  List<Brand> getAllBrand() throws CosmeticException;

  Brand getById(Long brandId) throws CosmeticException;

}
