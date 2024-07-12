package org.example.cosmeticwebpro.services.Impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.domains.Brand;
import org.example.cosmeticwebpro.domains.Product;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.request.BrandReqDTO;
import org.example.cosmeticwebpro.repositories.BrandRepository;
import org.example.cosmeticwebpro.repositories.ProductRepository;
import org.example.cosmeticwebpro.services.BrandService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BrandServiceImpl implements BrandService {
  private final BrandRepository brandRepository;
  private final ProductRepository productRepository;

  /**
   * create a new brand
   * @param brandReqDTO
   * @return
   * @throws CosmeticException
   */
  @Override
  public Brand create(BrandReqDTO brandReqDTO) throws CosmeticException {
    Brand brand =
        Brand.builder()
            .name(brandReqDTO.getName())
            .description(brandReqDTO.getDescription())
            .build();
    brandRepository.save(brand);
    return brand;
  }

  /**
   * update a brand
   */
  @Override
  public void update(Brand updateBrand) throws CosmeticException {
    Brand brand = this.getById(updateBrand.getId());
    brandRepository.save(updateBrand);
  }

  /**
   * find all brands in the shop
   * @return
   * @throws CosmeticException
   */
  @Override
  public List<Brand> getAllBrand() throws CosmeticException {
    List<Brand> brands = brandRepository.findAll();
    return brands;
  }

  /**
   * find a brand
   * @param brandId
   * @return
   * @throws CosmeticException
   */
  @Override
  public Brand getById(Long brandId) throws CosmeticException {
    var brand = brandRepository.findById(brandId);
    if (brand.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.BRAND_ID_NOT_FOUND,
          ExceptionUtils.messages.get(ExceptionUtils.BRAND_ID_NOT_FOUND));
    }
    return brand.get();
  }

  @Override
  public void delete(Long brandId) throws CosmeticException {
    // Get a list of products in this category
    List<Product> products = productRepository.findAllByCategoryId(brandId);

    // remove category_id information from products
    for (Product product : products) {
      product.setBrandId(null);
      productRepository.save(product);
    }
    var category = this.getById(brandId);
    brandRepository.delete(category);
  }
}
