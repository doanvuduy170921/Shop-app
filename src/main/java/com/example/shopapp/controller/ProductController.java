package com.example.shopapp.controller;

import com.example.shopapp.components.JwtTokenUtils;
import com.example.shopapp.components.LocalizationUtils;
import com.example.shopapp.dtos.ProductDto;
import com.example.shopapp.dtos.ProductImageDto;
import com.example.shopapp.dtos.ProductImagesDto;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductImage;
import com.example.shopapp.repositories.ProductImageRepository;
import com.example.shopapp.responses.ProductListResponse;
import com.example.shopapp.responses.ProductResponse;
import com.example.shopapp.services.IProductService;
import com.example.shopapp.services.impl.ProductService;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j(topic = "Product")
public class ProductController {
    protected final IProductService productService;
    private final LocalizationUtils localizationUtils;
    private final JwtTokenUtils jwtTokenUtils;
    private final ProductImageRepository productImageRepository;
    private  final ProductService productServiceImpl;


    @Value("${application.uploadFile}")
    private String uploadDir;


    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
        try {
            // Lấy đường dẫn ảnh từ thư mục uploadDir
            Path imagePath = Paths.get("Project-BitiTraining/productfile").resolve(filename);
            if (!Files.exists(imagePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Không tìm thấy file
            }

            // Trả về ảnh dưới dạng Resource
            Resource resource = new UrlResource(imagePath.toUri());

            // Kiểm tra định dạng file để trả về đúng content type
            String contentType = Files.probeContentType(imagePath);
            if (contentType == null) {
                contentType = "application/octet-stream";  // Kiểu mặc định nếu không xác định được
            }

            // Trả về đối tượng Resource và đặt loại nội dung phù hợp
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (MalformedURLException e) {
            // Xử lý lỗi nếu không thể tạo URL từ đường dẫn
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PostMapping(value = "uploads/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long productId,
            @RequestParam("files") List<MultipartFile> files
    ){
        try {
            Product existingProduct = productService.getProductById(productId);
            if (files == null) {
                files = new ArrayList<MultipartFile>();
            }
            if(files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
                return ResponseEntity.badRequest().body("You can only upload maximum 5 images");
            }
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if(file.getSize() == 0) {
                    continue;
                }
                // Kiểm tra kích thước file và định dạng
                if(file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                // Lưu file và cập nhật thumbnail trong DTO
                String filename = jwtTokenUtils.storeFile(file); // Thay thế hàm này với code của bạn để lưu file
                //lưu vào đối tượng product trong DB
                ProductImage productImage = productService.createProductImage(
                        existingProduct.getId(),
                        ProductImageDto.builder()
                                .imageUrl(filename)
                                .build()
                );
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @ResponseBody
    @RequestMapping(value = "/list-image/{productId}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listImages(@PathVariable Long productId) {
    try {
        List<String> images = productImageRepository.findAllByProductId(productId);
        List<String> fullLinkImages;
        if (images.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            fullLinkImages = new ArrayList<>();
            for (String image : images) {
                fullLinkImages.add("http://localhost:8080/api/v1/products/images/" + image);
            }
        }
        return ResponseEntity.ok().body(fullLinkImages);
    }catch (Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fetching images.");
    }
    }


    @PostMapping("/add")
    public ResponseEntity<?> createProduct(
             @RequestBody ProductDto productDto,
            BindingResult result
    ) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Product newProduct = productService.createProduct(productDto);
            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }





    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long id) {
        try {
            Product existingProduct = productService.getProductById(id);
            return ResponseEntity.ok(existingProduct);
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAllProduct")
    public ResponseEntity<ProductListResponse> getProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0",name = "category_id") Long categoryId,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        // Tạo pageable lấy thông tin từ trang và giới hạn
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                //Sort.by("createdAt").descending()
                Sort.by("id").ascending()
        );
        Page<ProductResponse> productPage = productService.getAllProducts(keyword,categoryId,pageRequest);
        // Lấy tổng số trang
        int totalPages = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse
                        .builder()
                        .products(products)
                        .totalPage(totalPages)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatedProduct(
            @PathVariable Long id,
            @RequestBody ProductDto productDto){
        try {
            Product updatedProduct = productService.updateProduct(id,productDto);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletedProduct(@PathVariable Long id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(String.format("Deleted product with ID : %d", id));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*@PostMapping("/generateFakerProducts")
    private ResponseEntity<?> generateFakerProducts(){
        Faker faker = new Faker();
        for(int i =0;i < 1000;i++){
            String productName = faker.commerce().productName();
            if(productService.existsByName(productName)){
                continue;
            }

            ProductDto productDto = ProductDto.builder()
                    .name(productName)
                    .price(faker.number().numberBetween(10,10000))
                    .description(faker.lorem().sentence())
                    .thumbnail("")
                    .categoryId((long)faker.number().numberBetween(2,5))
                    .build();
            try {
                productService.createProduct(productDto);
            } catch (Exception e) {
               return ResponseEntity.badRequest().body(e.getMessage());
                }
        }
        return ResponseEntity.ok().build();
    }*/
    @GetMapping("/search-by-criteria")
    public List<Product> searchByCriteria(
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String name){

        return productService.searchProductByCriteria(categoryName,minPrice,maxPrice,name);
    }
}

