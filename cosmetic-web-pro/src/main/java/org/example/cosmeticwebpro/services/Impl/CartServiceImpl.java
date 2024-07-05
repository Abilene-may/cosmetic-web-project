package org.example.cosmeticwebpro.services.Impl;

import jakarta.transaction.Transactional;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.commons.Constants;
import org.example.cosmeticwebpro.domains.Cart;
import org.example.cosmeticwebpro.domains.CartLine;
import org.example.cosmeticwebpro.domains.Product;
import org.example.cosmeticwebpro.domains.User;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.CartLineDTO;
import org.example.cosmeticwebpro.models.request.CartDisplayDTO;
import org.example.cosmeticwebpro.models.request.CartReqDTO;
import org.example.cosmeticwebpro.repositories.CartLineRepository;
import org.example.cosmeticwebpro.repositories.CartRepository;
import org.example.cosmeticwebpro.repositories.DiscountRepository;
import org.example.cosmeticwebpro.repositories.ProductImageRepository;
import org.example.cosmeticwebpro.repositories.ProductRepository;
import org.example.cosmeticwebpro.services.CartService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
  private final CartRepository cartRepository;
  private final CartLineRepository cartLineRepository;
  private final DiscountRepository discountRepository;
  private final ProductRepository productRepository;
  private final ProductImageRepository productImageRepository;

  /** Create a shopping cart when customer successfully registers for an account */
  @Override
  public void createCart(User user) {
    LocalDateTime localDateTime = LocalDateTime.now();
    Cart cart = new Cart();
    cart.setUser(user);
    cart.setCreatedDate(localDateTime);
    cartRepository.save(cart);
  }

  /** get total quantity a cart by user id */
  @Override
  public Integer getTotalQuantityCart(Long userId) {
    var cartDetails = cartLineRepository.findAllByUserId(userId);
    return cartDetails.size();
  }

  /** show all products in a cart */
  @Override
  public CartDisplayDTO getAllCartLineByCartId(Long cartId) throws CosmeticException {
    // check if shopping cart exists
    var cart = cartRepository.findById(cartId);
    if (cart.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.CART_DOES_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.CART_DOES_NOT_EXIST));
    }
    var cartLines = cartLineRepository.findAllByCartId(cartId);
    CartDisplayDTO cartDisplayDTO = new CartDisplayDTO();
    List<CartLineDTO> cartLineDTOS = new ArrayList<>();
    Integer totalItems = 0;
    double totalCost = 0.0;
    double totalFinalPrice = 0.0;
    if (cartLines.isEmpty()) {
      cartDisplayDTO.setCartLineDTOS(cartLineDTOS);
      cartDisplayDTO.setTotalItems(totalItems);
      cartDisplayDTO.setTotalCost(totalCost);
      cartDisplayDTO.setTotalFinalPrice(totalFinalPrice);
      return cartDisplayDTO;
    }
    for (CartLine cartLine : cartLines) {
      Long productId = cartLine.getProductId();
      Product product =
          productRepository
              .findById(productId)
              .orElseThrow(() -> new CosmeticException("Product not found with id: " + productId));
      double discount = 0;
      if (product.getDisCountId() != null) {
        var discountProduct = discountRepository.findById(product.getDisCountId());
        if (discountProduct.isPresent()) {
          discount = (double) discountProduct.get().getDiscountPercent() / 100;
        }
      }
      // cost after discount
      double cost =
          (product.getCurrentCost() - product.getCurrentCost() * discount) * cartLine.getQuantity();

      var productImages = productImageRepository.findProductImagesByProductId(productId);
      // get an image from a list
      String imageUrl = productImages.isEmpty() ? null : productImages.get(0).getImageUrl();
      totalItems++;
      totalCost = totalCost + cost;
      CartLineDTO cartLineDTO =
          CartLineDTO.builder()
              .imageUrl(imageUrl)
              .title(product.getTitle())
              .price(cost)
              .quantity(cartLine.getQuantity())
              .build();
      cartLineDTOS.add(cartLineDTO);
    }
    totalFinalPrice = totalCost;
    // Find the appropriate discount
    // Get the current LocalDateTime
    LocalDateTime today = LocalDateTime.now();

    ZonedDateTime zonedDateTime = today.atZone(ZoneId.systemDefault());
    Date todayDate = Date.from(zonedDateTime.toInstant());
    // Convert LocalDateTime to Date
    var bestDiscountForOrder = discountRepository.findBestDiscountForOrder(totalFinalPrice, Constants.ORDER, todayDate);
    if (bestDiscountForOrder.isPresent()) {
      cartDisplayDTO.setDiscount(bestDiscountForOrder.get());
      totalFinalPrice =
          totalCost - (totalCost * ((double) bestDiscountForOrder.get().getDiscountPercent() / 100));
    }
    cartDisplayDTO.setCartLineDTOS(cartLineDTOS);
    cartDisplayDTO.setTotalItems(totalItems);
    cartDisplayDTO.setTotalCost(totalCost);
    cartDisplayDTO.setTotalFinalPrice(totalFinalPrice);
    return cartDisplayDTO;
  }

  /** add a new product into the cart */
  @Transactional
  @Override
  public CartLine addANewProduct(CartReqDTO cartReqDTO) throws CosmeticException {
    var product =
        cartLineRepository.findAllByProductId(cartReqDTO.getProductId(), cartReqDTO.getCartId());
    LocalDateTime localDateTime = LocalDateTime.now();
    // case the product is already in the cart
    if (product.isPresent()) {
      CartReqDTO reqDTO =
          CartReqDTO.builder()
              .productId(cartReqDTO.getProductId())
              .cartId(cartReqDTO.getCartId())
              .quantity(cartReqDTO.getQuantity())
              .build();
      return this.updateCart(reqDTO);
    }
    CartLine cartLine =
        CartLine.builder()
            .productId(cartReqDTO.getProductId())
            .cartId(cartReqDTO.getCartId())
            .quantity(cartReqDTO.getQuantity())
            .createdDate(localDateTime)
            .modifiedDate(localDateTime)
            .build();
    return cartLineRepository.save(cartLine);
  }

  @Override
  public CartLine updateCart(CartReqDTO cartReqDTO) throws CosmeticException {
    var product = this.checkExistProduct(cartReqDTO.getProductId(), cartReqDTO.getCartId());
    LocalDateTime localDateTime = LocalDateTime.now();
    product.setQuantity(product.getQuantity() + cartReqDTO.getQuantity());
    product.setModifiedDate(localDateTime);
    return cartLineRepository.save(product);
  }

  /** delete a product from the shopping cart */
  @Override
  public void deleteAProduct(Long productId, Long cartId) throws CosmeticException {
    var cartLine = this.checkExistProduct(productId, cartId);
    cartLineRepository.delete(cartLine);
  }

  // get cart by user id
  @Override
  public Cart getCartByUserId(Long userId) throws CosmeticException {
    var cart = cartRepository.findByUserId(userId);
    if (cart.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.CART_DOES_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.CART_DOES_NOT_EXIST));
    }
    return cart.get();
  }

  CartLine checkExistProduct(Long productId, Long cartId) throws CosmeticException {
    var product = cartLineRepository.findAllByProductId(productId, cartId);
    if (product.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.PRODUCT_IS_NOT_FOUND_IN_THE_CART,
          ExceptionUtils.messages.get(ExceptionUtils.PRODUCT_IS_NOT_FOUND_IN_THE_CART));
    }
    return product.get();
  }
}
