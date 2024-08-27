package com.example.shopapp.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDto {

    @JsonProperty("product_id")
    @Min(value = 1,message = "Product Id must be greater to 0")
    private Long productId;

    @JsonProperty("image_url")
    @Size(min = 5, max = 200, message = "Image's name")
    private String imageUrl;
}
