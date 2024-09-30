package com.example.shopapp.services;

import com.example.shopapp.model.Cart;

public interface ICartService {
    Cart addToCart(Cart cart ,Long productId);
}
