package com.example.shopapp.repositories;

import com.example.shopapp.model.Cart;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
    Cart findByUserAndProduct(User user, Product product);
}
