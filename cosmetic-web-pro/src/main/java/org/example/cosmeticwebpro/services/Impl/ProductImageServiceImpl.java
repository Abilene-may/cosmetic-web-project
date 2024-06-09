package org.example.cosmeticwebpro.services.Impl;

import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.domains.ProductImage;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.repositories.ProductImageRepository;
import org.example.cosmeticwebpro.services.CloudinaryService;
import org.example.cosmeticwebpro.services.ProductImageService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<ProductImage> list(){
        return imageRepository.findByOrderById();
    }

    @Override
    public Optional<ProductImage> getOne(Long id){
        return imageRepository.findById(id);
    }

    @Override
    public void save(ProductImage productImage) {
        imageRepository.save(productImage);
    }

    @Override
    public void delete(Long id) throws IOException {
        ProductImage productImage = imageRepository.findById(id).get();
        cloudinaryService.delete(productImage.getImageId());
        imageRepository.deleteById(id);
    }
    @Override
    public boolean exists(Long id){
        return imageRepository.existsById(id);
    }

    // get all images for a product
    @Override
    public List<ProductImage> getAllByProductId(Long productId) throws CosmeticException {
        var productImages = imageRepository.findProductImagesByProductId(productId);
        return productImages;
    }

    @Override
    public List<ProductImage> getAll() {
        return imageRepository.findAll();
    }
}
