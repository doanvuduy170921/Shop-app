package com.example.shopapp.services;

import com.example.shopapp.model.Cart;
import com.example.shopapp.responses.CartResponseDto;

public interface ICartService {
    CartResponseDto addToCart(Cart cart , Long productId);


}
