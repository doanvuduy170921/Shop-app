package com.example.shopapp.requests;

import com.example.shopapp.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //toString
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private Cart cart;

}
