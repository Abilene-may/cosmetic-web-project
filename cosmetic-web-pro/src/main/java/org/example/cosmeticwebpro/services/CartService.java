package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.domains.CartLine;
import org.example.cosmeticwebpro.domains.User;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.request.CartReqDTO;

public interface CartService {
    void createCart(User user);

    Integer getTotalQuantityCart(Long userId);

    List<CartLine> getAllByCartId(Long cartId) throws CosmeticException;

    CartLine addANewProduct(CartReqDTO cartReqDTO) throws CosmeticException;

    CartLine updateCart(CartReqDTO cartReqDTO) throws CosmeticException;

    void deleteAProduct(Long productId, Long cartId) throws CosmeticException;
}
