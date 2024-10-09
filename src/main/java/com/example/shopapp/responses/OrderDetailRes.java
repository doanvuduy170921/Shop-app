package com.example.shopapp.responses;

import com.example.shopapp.dtos.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRes {
    private Long id;

    private ProductDto product;

    private Float price;

    private  int quantity;

    private Float totalMoney;
}
