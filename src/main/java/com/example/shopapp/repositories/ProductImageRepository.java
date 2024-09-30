package com.example.shopapp.repositories;

import com.example.shopapp.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProductId(Long productId);

    @Query(value = "SELECT image_url  FROM product_images WHERE product_id = :id;", nativeQuery = true)
    List<String> findAllByProductId(@Param("id") Long id);
}
