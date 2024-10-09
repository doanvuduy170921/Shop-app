package com.example.shopapp.controller;

import com.example.shopapp.filters.JwtTokenFilter;
import com.example.shopapp.repositories.CartRepository;
import com.example.shopapp.requests.CartRequestDto;
import com.example.shopapp.model.Cart;
import com.example.shopapp.responses.CartResponseDto;
import com.example.shopapp.responses.ListCartResDto;
import com.example.shopapp.services.impl.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {

    private final CartRepository cartRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

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


    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable("id") Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(()-> new RuntimeException("CartId not found"));
        return ResponseEntity.ok().body(cart);
    }

    @GetMapping("/user-cart-by-ids")
    public ResponseEntity<ListCartResDto> getCartByUserAndListId(
            @RequestParam List<Long> ids) { // Thêm list id từ query parameter
        String username = JwtTokenFilter.CURRENT_USER; // Lấy user từ token
        List<CartResponseDto> cartProducts = cartService.getCartByUserAndListId(username, ids); // Truyền list id vào service
        double totalPrice = cartProducts.stream()
                .mapToDouble(p -> p.getPrice() * p.getQuantity())
                .sum();
        return ResponseEntity.ok(new ListCartResDto(cartProducts, totalPrice));
    }


    @GetMapping("/search-by-criteria")
    public List<Cart> searchByCriteria(
            @RequestParam String categoryName,
            @RequestParam Integer quantity,
            @RequestParam Integer min,
            @RequestParam Integer max,
            @RequestParam String keyword
            ) {
        return cartService.searchCartWithComplexCriteria(categoryName,quantity,min,max,keyword);
    }
}
