package org.example.cosmeticwebpro.services.Impl;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.domains.Cart;
import org.example.cosmeticwebpro.domains.CartDetail;
import org.example.cosmeticwebpro.domains.User;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.request.CartReqDTO;
import org.example.cosmeticwebpro.repositories.CartDetailRepository;
import org.example.cosmeticwebpro.repositories.CartRepository;
import org.example.cosmeticwebpro.services.CartService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;

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
     * get total quantity a cart by id
     */
    @Override
    public Integer getTotalQuantityCart(Long cartId) {
        var cartDetails = cartDetailRepository.findAllByCartId(cartId);
        return cartDetails.size() - 1;
    }

    /**
     * find all product in a cart
     */
    @Override
    public List<CartDetail> getAllByCartId(Long cartId) throws CosmeticException {
        var cartDetails = cartDetailRepository.findAllByCartId(cartId);
        return cartDetails;
    }

    /**
     * add a new product into the cart
     *
     */
    @Transactional
    @Override
    public CartDetail addANewProduct(CartReqDTO cartReqDTO) throws CosmeticException {
      var product =
          cartDetailRepository.findAllByProductId(cartReqDTO.getProductId(), cartReqDTO.getCartId());
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
      CartDetail newCartDetail = CartDetail.builder()
          .productId(cartReqDTO.getProductId())
          .cartId(cartReqDTO.getCartId())
          .quantity(cartReqDTO.getQuantity())
          .createdDate(localDateTime)
          .modifiedDate(localDateTime)
          .build();
      return cartDetailRepository.save(newCartDetail);
    }

    @Override
    public CartDetail updateCart(CartReqDTO cartReqDTO) throws CosmeticException {
      var product = this.checkExistProduct(cartReqDTO.getProductId(), cartReqDTO.getCartId());
      LocalDateTime localDateTime = LocalDateTime.now();
      product.setQuantity(product.getQuantity() + cartReqDTO.getQuantity());
      product.setModifiedDate(localDateTime);
      return cartDetailRepository.save(product);
    }

  /**
   * delete a product from the shopping cart
   */
  @Override
  public void deleteAProduct(Long productId, Long cartId) throws CosmeticException {
    var cartDetail = this.checkExistProduct(productId, cartId);
    cartDetailRepository.delete(cartDetail);
  }

  CartDetail checkExistProduct(Long productId, Long cartId) throws CosmeticException{
    var product =
        cartDetailRepository.findAllByProductId(productId, cartId);
    if (product.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.PRODUCT_IS_NOT_FOUND_IN_THE_CART,
          ExceptionUtils.messages.get(ExceptionUtils.PRODUCT_IS_NOT_FOUND_IN_THE_CART));
    }
    return product.get();
  }

}
