package com.example.shopapp.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    @NotEmpty(message = "Category name is not empty")
    private String name;
}
