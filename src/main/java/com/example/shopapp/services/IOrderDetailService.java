package com.example.shopapp.services;

import com.example.shopapp.dtos.OrderDetailDto;
import com.example.shopapp.model.OrderDetail;

import java.util.List;

public interface IOrderDetailService {

    OrderDetail createDetail(OrderDetailDto newOrderDetail);

    OrderDetail getDetailById(Long id);

    List<OrderDetail> getAllDetails(Long orderId);

    OrderDetail updateDetail(Long id, OrderDetailDto newOrderDetail);

    void deleteDetail(Long id);


}
