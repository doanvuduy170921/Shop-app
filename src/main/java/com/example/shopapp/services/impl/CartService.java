package com.example.shopapp.services.impl;

import com.example.shopapp.filters.JwtTokenFilter;
import com.example.shopapp.model.Cart;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.User;
import com.example.shopapp.repositories.CartRepository;
import com.example.shopapp.repositories.ProductRepository;
import com.example.shopapp.repositories.UserRepository;
import com.example.shopapp.services.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public Cart addToCart(Cart cart, Long productId) {
    Product product = productRepository.findById(productId).get();
    String username = JwtTokenFilter.CURRENT_USER;
    User user = userRepository.findByPhoneNumber(username).get();

//    User user = userRepository.findById(userId).get();

    if(product != null && user != null) {
        cart.setProduct(product);
        cart.setUser(user);
    }
        return cartRepository.save(cart);

    }
}
