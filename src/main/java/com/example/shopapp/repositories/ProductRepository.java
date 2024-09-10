package com.example.shopapp.repositories;

import com.example.shopapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String title);
    Page<Product> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM products\n" +
            "JOIN categories ON products.category_id = categories.id\n" +
            "WHERE (:categoryId IS NULL OR categories.id =:categoryId)\n" +
            "  AND (:keyword IS NULL OR  products.name like  %:keyword% or products.description like %:keyword%)",
            countQuery = "SELECT count(*) FROM products\n" +
                    "JOIN categories ON products.category_id = categories.id\n" +
                    "WHERE (:categoryId IS NULL OR categories.id =:categoryId)\n" +
                    "  AND (:keyword IS NULL OR  products.name like  %:keyword% or products.description like %:keyword%)",nativeQuery = true)
    Page<Product> searchProducts(@Param("categoryId") Long categoryId,@Param("keyword") String keyword ,Pageable pageable);
}
