package com.example.shopapp.services;

import com.example.shopapp.dtos.ProductDto;
import com.example.shopapp.dtos.ProductImageDto;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.exceptions.InvalidParamException;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


public interface IProductService {
    Product createProduct(ProductDto productDto) throws Exception;

    Product getProductById(long id) throws Exception;

    Page<Product> getAllProducts(PageRequest pageRequest);

    Product updateProduct(long id, ProductDto productDto) throws Exception;

    void deleteProduct(long id);

    boolean existsByName(String name);

    public ProductImage createProductImage(
            Long productId,
            ProductImageDto productImageDto) throws Exception;
}
