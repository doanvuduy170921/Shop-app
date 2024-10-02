package com.example.shopapp.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //toString
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestDto {

    private Long productId;
    private int quantity;
}
