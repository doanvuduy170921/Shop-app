package com.example.shopapp.services.impl;

import com.example.shopapp.dtos.UserDto;
import com.example.shopapp.filters.JwtTokenFilter;
import com.example.shopapp.model.Cart;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.User;
import com.example.shopapp.repositories.CartRepository;
import com.example.shopapp.repositories.ProductRepository;
import com.example.shopapp.repositories.UserRepository;
import com.example.shopapp.responses.CartResponseDto;
import com.example.shopapp.responses.UserRes;
import com.example.shopapp.services.ICartService;
import com.example.shopapp.specification.CartSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private  final ModelMapper modelMapper;

    @Override
    public CartResponseDto addToCart(Cart cart, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        String username = JwtTokenFilter.CURRENT_USER;
        User user = userRepository.findByPhoneNumber(username).orElseThrow(() -> new RuntimeException("User not found"));

        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng của user chưa
        Cart existingCart = cartRepository.findByUserAndProduct(user, product);
        if (existingCart != null) {
            // Nếu sản phẩm đã có trong giỏ hàng, cập nhật số lượng
            existingCart.setQuantity(existingCart.getQuantity() + cart.getQuantity());
            Cart updatedCart = cartRepository.save(existingCart);

            return CartResponseDto.builder()
                    .thumbnail(updatedCart.getProduct().getThumbnail())
                    .productName(updatedCart.getProduct().getName())
                    .quantity(updatedCart.getQuantity())
                    .price(updatedCart.getProduct().getPrice())
                    .build();
        } else {
            // Nếu sản phẩm chưa có trong giỏ hàng, thêm mới
            cart.setProduct(product);
            cart.setUser(user);
            Cart savedCart = cartRepository.save(cart);

            return CartResponseDto.builder()
                    .thumbnail(savedCart.getProduct().getThumbnail())
                    .productName(savedCart.getProduct().getName())
                    .quantity(savedCart.getQuantity())
                    .price(savedCart.getProduct().getPrice())
                    .build();
        }
    }

    @Override
    public List<Cart> searchCartWithComplexCriteria(
            String categoryName, Integer quantity,
            Integer min, Integer max, String keyword) {

        Specification<Cart> spec = Specification
                .where(CartSpecification.hasCategory(categoryName))
                .and(CartSpecification.hasPriceInRange(min,max))
                .and(CartSpecification.hasQuantityGreaterThan(quantity))
                .and(CartSpecification.hasProductNameLike(keyword));
        return cartRepository.findAll(spec);
    }

    public List<CartResponseDto> getCartByUser(String username) {
        // Lấy thông tin user từ username (số điện thoại)
        User user = userRepository.findByPhoneNumber(username).orElseThrow(() -> new RuntimeException("User not found"));

        // Lấy danh sách các sản phẩm trong giỏ hàng của user
        List<Cart> carts = cartRepository.findByUser(user);

        // Chuyển đổi từ Cart entity sang DTO
        return carts.stream().map(cart -> new CartResponseDto(
                cart.getId(),
                cart.getProduct().getThumbnail(),
                cart.getProduct().getName(),
                cart.getQuantity(),
                cart.getProduct().getPrice(),
                this.modelMapper.map(cart.getUser(), UserRes.class)
        )).collect(Collectors.toList());
    }

    public List<CartResponseDto> getCartByUserAndListId(String username, List<Long> ids) {
        // Lấy thông tin user từ username (số điện thoại)
        User user = userRepository.findByPhoneNumber(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Lấy danh sách các sản phẩm trong giỏ hàng của user
        List<Cart> carts = cartRepository.findByUser(user);

        // Lọc các sản phẩm theo danh sách id được truyền vào
        List<Cart> filteredCarts = carts.stream()
                .filter(cart -> ids.contains(cart.getId()))
                .toList();

        // Chuyển đổi từ Cart entity sang DTO
        return filteredCarts.stream()
                .map(cart -> new CartResponseDto(
                        cart.getId(),
                        cart.getProduct().getThumbnail(),
                        cart.getProduct().getName(),
                        cart.getQuantity(),
                        cart.getProduct().getPrice(),
                        this.modelMapper.map(cart.getUser(), UserRes.class)
                ))
                .collect(Collectors.toList());
    }
}
