package com.example.shopapp.services.impl;

import com.example.shopapp.dtos.OrderDetailDto;
import com.example.shopapp.model.OrderDetail;
import com.example.shopapp.repositories.OrderDetailRepository;
import com.example.shopapp.services.IOrderDetailService;
import com.example.shopapp.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetail createDetail(OrderDetailDto newOrderDetail) {
        OrderDetail orderDetail ;
        return null;
    }

    @Override
    public OrderDetail getDetailById(Long id) {
        return null;
    }

    @Override
    public List<OrderDetail> getAllDetails(Long orderId) {
        return List.of();
    }

    @Override
    public OrderDetail updateDetail(Long id, OrderDetailDto newOrderDetail) {
        return null;
    }

    @Override
    public void deleteDetail(Long id) {

    }
}
