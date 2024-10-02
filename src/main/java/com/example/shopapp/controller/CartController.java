package com.example.shopapp.controller;

import com.example.shopapp.filters.JwtTokenFilter;
import com.example.shopapp.requests.CartRequestDto;
import com.example.shopapp.model.Cart;
import com.example.shopapp.responses.CartResponseDto;
import com.example.shopapp.responses.ListCartResDto;
import com.example.shopapp.services.impl.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<CartResponseDto> addToCart(@RequestBody CartRequestDto cartRequest) {
        Cart cart = new Cart();
        cart.setQuantity(cartRequest.getQuantity());
        return ResponseEntity.ok().body(cartService.addToCart(cart,cartRequest.getProductId()));
    }

    @GetMapping("/user-cart")
    public ResponseEntity<ListCartResDto> getCartByUser() {
        String username = JwtTokenFilter.CURRENT_USER; // Lấy user từ token
        List<CartResponseDto> cartProducts = cartService.getCartByUser(username);
        double totalPrice = cartProducts.stream().mapToDouble(p -> p.getPrice() * p.getQuantity()).sum();
        return ResponseEntity.ok(new ListCartResDto(cartProducts, totalPrice));
    }

}
