package com.example.shopapp.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CartResponseDto {
    private  Long id;

    private String thumbnail;

    private String productName;

    private int quantity;

    private int price;
    private UserRes user;
}
