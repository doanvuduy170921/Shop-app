package com.example.shopapp.services;

import com.example.shopapp.dtos.OrderDto;
import com.example.shopapp.model.Order;


import java.util.List;

public interface IOrderService {
   Order createOrder(OrderDto orderDto) throws Exception;

   Order updateOrder(Long id, OrderDto orderDto);

   List<Order> getAllOrder(Long userId);

   Order getOrder(OrderDto orderDto);

   void deleteOrderById(Long id);
}
