package org.example.cosmeticwebpro.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Set;
import org.example.cosmeticwebpro.domains.Product;
import org.example.cosmeticwebpro.models.ProductOverviewDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStruct {

  //     ======================= COVERT OBJECT PRODUCT =======================
  default <T> Set<T> stringToSet(String json, Class<T> clazz) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    JavaType type = objectMapper.getTypeFactory().constructCollectionType(Set.class, clazz);
    return objectMapper.readValue(json, type);
  }

  //     ======================= COVERT OBJECT PRODUCT =======================
  //     Product mapToProduct(ProductResponseDto productDto);
  ProductOverviewDTO mapToProductResponseDto(Product product);

  List<ProductOverviewDTO> mapToProductResponseDtoList(List<Product> products);
}
