package com.example.shopapp.services.impl;

import com.example.shopapp.dtos.ProductDto;
import com.example.shopapp.dtos.ProductImageDto;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.exceptions.InvalidParamException;
import com.example.shopapp.model.Category;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductImage;
import com.example.shopapp.repositories.CategoryRepository;
import com.example.shopapp.repositories.ProductImageRepository;
import com.example.shopapp.repositories.ProductRepository;
import com.example.shopapp.responses.ProductResponse;
import com.example.shopapp.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public Product createProduct(ProductDto productDto) throws DataNotFoundException {
        Category existingCategory = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Can not find category with id :" + productDto.getCategoryId()));
        Product newProduct = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .thumbnail(productDto.getThumbnail())
                .description(productDto.getDescription())
                .category(existingCategory)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(long productId) throws Exception {
        return productRepository.findById(productId).
                orElseThrow(() -> new DataNotFoundException("Can not find product with id: " + productId));
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        // Lấy danh sách sản phẩm theo trang(page) và giới hạn(limit)
        return productRepository
                .findAll(pageRequest)
                .map(ProductResponse::fromproduct
        );
    }

    @Override
    public Product updateProduct(
            long id,
            ProductDto productDto) throws Exception {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {
            // Coppy các thuộc tính từ DTO --> product
            // có thể sử dụng ModelMapper
            Category existingCategory = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new DataNotFoundException(
                            "Can not find category with id :" + productDto.getCategoryId()));
            existingProduct.setName(productDto.getName());
            existingProduct.setPrice(productDto.getPrice());
            existingProduct.setThumbnail(productDto.getThumbnail());
            existingProduct.setCategory(existingCategory);
            existingProduct.setDescription(productDto.getDescription());

            return productRepository.save(existingProduct);

        }
        return null;
    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(
            Long productId,
            ProductImageDto productImageDto) throws DataNotFoundException, InvalidParamException {

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException(
                        "Can not find product with id :" + productImageDto.getProductId()));
        ProductImage newProductImage = ProductImage
                .builder()
                .product(existingProduct)
                .imageUrl(productImageDto.getImageUrl())
                .build();
        // không insert quá 5 ảnh cho 1 sản phẩm
        int size = productImageRepository.findByProductId(existingProduct.getId()).size();
        if (size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException("Number of images must be less than "+ProductImage.MAXIMUM_IMAGES_PER_PRODUCT)  ;
        }
        return productImageRepository.save(newProductImage);
    }
}