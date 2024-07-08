package org.example.cosmeticwebpro.services;

import org.example.cosmeticwebpro.domains.Cart;
import org.example.cosmeticwebpro.domains.CartLine;
import org.example.cosmeticwebpro.domains.User;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.request.CartDisplayDTO;
import org.example.cosmeticwebpro.models.request.CartReqDTO;

public interface CartService {
    void createCart(User user);

    Integer getTotalQuantityCart(Long userId);

    CartDisplayDTO getAllCartLineByCartId(Long cartId) throws CosmeticException;

    CartLine addANewProduct(CartReqDTO cartReqDTO) throws CosmeticException;

    CartLine updateCart(CartReqDTO cartReqDTO) throws CosmeticException;

    void deleteAProduct(Long productId, Long cartId) throws CosmeticException;

    Cart getCartByUserId(Long userId) throws CosmeticException;

    void clearCartLine(Long userId) throws CosmeticException;
}
