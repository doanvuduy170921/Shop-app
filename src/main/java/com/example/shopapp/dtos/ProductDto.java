package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @NotBlank(message = "Title is required")
    @Size(min = 3,max = 200,message = "Title must be between 3 and 200 characters")
    private String name;

    @Min(value = 0,message = "Price must be greater or equals to 0")
    @Max(value = 10000000,message = "Price must be less than to 10000000")
    private Float price;
    private String thumbnail;
    private String description;

    @JsonProperty("category_id")
    private Long categoryId;


}
