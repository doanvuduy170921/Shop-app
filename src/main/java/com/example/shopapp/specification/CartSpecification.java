package com.example.shopapp.specification;

import com.example.shopapp.model.Cart;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class CartSpecification {
    // Join Cart và Category, lọc theo category name
    public static Specification<Cart> hasCategory(String categoryName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if(categoryName ==null || categoryName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<Object,Object> product = root.join("product");
            Join<Object,Object> category = product.join("category");
            return criteriaBuilder.equal(category.get("name"), categoryName);
        };
    }
    // Lọc sản phẩm có số lượng product trong cart lớn hơn giá trị cho trước
    public static Specification<Cart> hasQuantityGreaterThan(int quantity) {
        return ((root, query, criteriaBuilder) -> {
           if(quantity <= 0){
               return criteriaBuilder.conjunction();
           }
           return criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), quantity);
        });
    }
    // Lọc sản phẩm có giá từ minPrice đến maxPrice
    public static Specification<Cart> hasPriceInRange(Integer min, Integer max) {
        return ((root, query, criteriaBuilder) -> {
            Join<Object,Object> product = root.join("product");
            if(min!=null && max !=null){
                return criteriaBuilder.between(product.get("price"), min, max);
            }
            else if(min!=null ){
                return criteriaBuilder.greaterThanOrEqualTo(product.get("price"), min);
            }
            else if(max!=null ){
                return criteriaBuilder.lessThanOrEqualTo(product.get("price"), max);
            }
            return criteriaBuilder.conjunction();
        });
    }
    // Lọc sản phẩm theo keyword nhập vào
    public static Specification<Cart> hasProductNameLike(String keyword) {
        return ((root, query, criteriaBuilder) -> {
            if(keyword == null || keyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<Object,Object> product = root.join("product");
            return criteriaBuilder.like(criteriaBuilder.lower(product.get("name")), "%" + keyword.toLowerCase() + "%");
        });
    }
}
