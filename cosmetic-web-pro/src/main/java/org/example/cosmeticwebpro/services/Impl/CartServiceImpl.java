package org.example.cosmeticwebpro.services.Impl;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.domains.Cart;
import org.example.cosmeticwebpro.domains.CartLine;
import org.example.cosmeticwebpro.domains.User;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.request.CartReqDTO;
import org.example.cosmeticwebpro.repositories.CartLineRepository;
import org.example.cosmeticwebpro.repositories.CartRepository;
import org.example.cosmeticwebpro.services.CartService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartLineRepository cartLineRepository;

    /**
     * Create a shopping cart when customer successfully registers for an  account
     */
    @Override
    public void createCart(User user) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCreatedDate(localDateTime);
        cartRepository.save(cart);
    }

    /**
     * get total quantity a cart by user id
     */
    @Override
    public Integer getTotalQuantityCart(Long userId) {
        var cartDetails = cartLineRepository.findAllByUserId(userId);
        return cartDetails.size();
    }

    /**
     * find all product in a cart
     */
    @Override
    public List<CartLine> getAllByCartId(Long cartId) throws CosmeticException {
        var cartDetails = cartLineRepository.findAllByCartId(cartId);
        return cartDetails;
    }

    /**
     * add a new product into the cart
     *
     */
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
      CartLine newCartDetail = CartLine.builder()
          .productId(cartReqDTO.getProductId())
          .cartId(cartReqDTO.getCartId())
          .quantity(cartReqDTO.getQuantity())
          .createdDate(localDateTime)
          .modifiedDate(localDateTime)
          .build();
      return cartLineRepository.save(newCartDetail);
    }

    @Override
    public CartLine updateCart(CartReqDTO cartReqDTO) throws CosmeticException {
      var product = this.checkExistProduct(cartReqDTO.getProductId(), cartReqDTO.getCartId());
      LocalDateTime localDateTime = LocalDateTime.now();
      product.setQuantity(product.getQuantity() + cartReqDTO.getQuantity());
      product.setModifiedDate(localDateTime);
      return cartLineRepository.save(product);
    }

  /**
   * delete a product from the shopping cart
   */
  @Override
  public void deleteAProduct(Long productId, Long cartId) throws CosmeticException {
    var cartDetail = this.checkExistProduct(productId, cartId);
    cartLineRepository.delete(cartDetail);
  }

  CartLine checkExistProduct(Long productId, Long cartId) throws CosmeticException{
    var product =
        cartLineRepository.findAllByProductId(productId, cartId);
    if (product.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.PRODUCT_IS_NOT_FOUND_IN_THE_CART,
          ExceptionUtils.messages.get(ExceptionUtils.PRODUCT_IS_NOT_FOUND_IN_THE_CART));
    }
    return product.get();
  }

}
