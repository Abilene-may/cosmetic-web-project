package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.domains.CartDetail;
import org.example.cosmeticwebpro.domains.User;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.request.CartReqDTO;

public interface CartService {
    void createCart(User user);

    Integer getTotalQuantityCart(Long userId);

    List<CartDetail> getAllByCartId(Long cartId) throws CosmeticException;

    CartDetail addANewProduct(CartReqDTO cartReqDTO) throws CosmeticException;

    CartDetail updateCart(CartReqDTO cartReqDTO) throws CosmeticException;

    void deleteAProduct(Long productId, Long cartId) throws CosmeticException;
}
