package com.example.shopapp.controller;

import com.example.shopapp.model.Cart;
import com.example.shopapp.services.impl.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;

    @PostMapping("/add-to-cart/{productId}")
    public ResponseEntity<Cart> addToCart(@RequestBody Cart cart, @PathVariable Long productId) {

         return ResponseEntity.ok().body(cartService.addToCart(cart,productId));
    }
}
