package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {

    @JsonProperty("user_id")
    @Min(value = 1,message = "User Id must be > 0")
    private Long userId;

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;


    private String address;

    private String email;
    private  String note;

    @JsonProperty("total_money")
    @Min(value = 0, message = "total money greater or equals to 0")
    private Float totalMoney;

    @JsonProperty("shipping_method")
    private Float shippingMethod;
    @JsonProperty("shipping_address")
    private Float shippingAddress;

    @JsonProperty("payment_method")
    private Float paymentMethod;
}
