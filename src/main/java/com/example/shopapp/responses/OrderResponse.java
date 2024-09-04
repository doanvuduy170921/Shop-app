package com.example.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class OrderResponse extends BaseResponse{

    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    private String fullname;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String address;

    private String note;

    @JsonProperty("oder_date")
    private LocalDateTime orderDate;

    private String status;

    @JsonProperty("total_money")
    private Float totalMoney;
}
