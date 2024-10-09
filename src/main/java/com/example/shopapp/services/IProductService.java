package com.example.shopapp.services;

import com.example.shopapp.dtos.ProductDto;
import com.example.shopapp.dtos.ProductImageDto;

import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductImage;
import com.example.shopapp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;


public interface IProductService {
    Product createProduct(ProductDto productDto) throws Exception;

    Product getProductById(long id) throws Exception;

    Page<ProductResponse> getAllProducts(String keyword,Long categoryId,PageRequest pageRequest);

    Product updateProduct(long id, ProductDto productDto) throws Exception;

    void deleteProduct(long id);

    boolean existsByName(String name);

    public ProductImage createProductImage(Long productId, ProductImageDto productImageDto) throws Exception;

    List<Product> searchProductByCriteria(String categoryName, Integer minPrice, Integer maxPrice,String name);
}
