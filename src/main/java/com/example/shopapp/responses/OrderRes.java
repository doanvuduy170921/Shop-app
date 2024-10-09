package com.example.shopapp.responses;

import com.example.shopapp.model.OrderDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderRes {

    private Long id;

    private UserRes user;


    private String phoneNumber;

    private String address;

    private String note;

    @JsonProperty("order_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDate;

    private String status;

    private Float totalMoney;

    private List<OrderDetailRes>  orderDetails = new ArrayList<>();
}
