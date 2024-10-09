package com.example.shopapp.services;

import com.example.shopapp.model.Cart;
import com.example.shopapp.responses.CartResponseDto;

import java.util.List;

public interface ICartService {
    CartResponseDto addToCart(Cart cart , Long productId);


    List<Cart> searchCartWithComplexCriteria(String categoryName,Integer minQuantity,Integer min, Integer max,String keyword);
}
