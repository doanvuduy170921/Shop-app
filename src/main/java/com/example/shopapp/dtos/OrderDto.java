package com.example.shopapp.dtos;

import com.example.shopapp.responses.OrderDetailRes;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    @JsonProperty("address")
    private String address;

    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("total_quantity")
    private int totalQuantity;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("shipping_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate shippingDate;

    @JsonProperty("payment_method")
    private String paymentMethod;

    private List<OrderDetailRes> orderDetails;
    @JsonProperty("order_code")
    private String orderCode;

    @JsonProperty("order_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date orderDate;
}
