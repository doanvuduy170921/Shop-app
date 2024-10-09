package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {

    private Long id;

    @JsonProperty("order_id")
    @Min(value = 1,message = "orderId must be greater to 0")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1,message = "productId must be greater to 0")
    private Long productId;

    @Min(value = 0,message = "productId must be greater or equals to 0")
    private Float price;


    @Min(value = 1,message = "number_of_product must be greater to 0")
    private Long quantity;

    @JsonProperty("total_money")
    @Min(value = 0,message = "totalMoney must be greater or equals to 0")
    private Float totalMoney;

    @JsonProperty("order_code")
    private String orderCode;


    public String generateOrderCode(Long orderId) {
        // Example logic to generate order code
        return "#" + orderId + "SH" + System.currentTimeMillis();
    }

}
