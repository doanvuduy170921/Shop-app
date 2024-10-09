package com.example.shopapp.specification;

import com.example.shopapp.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> hasCategory(String categoryName) {
        return (root, query, criteriaBuilder) -> {
            if(categoryName== null || categoryName.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("category").get("name"), categoryName);
        };
    }
    public static Specification<Product> hasPriceInRange(Integer minPrice, Integer maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if(minPrice == null || maxPrice == null ){
                return criteriaBuilder.conjunction();
            }
            if(minPrice == null){
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
            if(maxPrice == null){
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            }
            return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
        };
    }
    public static Specification<Product> hasNamelike(String name) {
        return (root, query, criteriaBuilder) -> {
          if(name == null || name.isEmpty()){
              return criteriaBuilder.conjunction();
          }
          return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }
}
