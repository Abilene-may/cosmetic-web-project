package org.example.cosmeticwebpro.services.Impl;

import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.domains.Cart;
import org.example.cosmeticwebpro.domains.User;
import org.example.cosmeticwebpro.repositories.CartRepository;
import org.example.cosmeticwebpro.services.CartService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;

    /**
     * Create a shopping cart when customer successfully registers for an  account
     */
    @Override
    public void createCart(User user) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Cart cart = new Cart();
        cart.setTotalQuantity(0);
        cart.setUser(user);
        cart.setCreatedDate(localDateTime);
        cartRepository.save(cart);
    }

}
