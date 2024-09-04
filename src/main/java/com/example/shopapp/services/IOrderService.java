package com.example.shopapp.services;

import com.example.shopapp.dtos.OrderDto;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.model.Order;
import com.example.shopapp.responses.OrderResponse;

import java.util.List;

public interface IOrderService {
   Order createOrder(OrderDto orderDto) throws Exception;

   Order updateOrder(Long id, OrderDto orderDto);

   List<Order> getAllOrder(Long userId);

   Order getOrder(OrderDto orderDto);

   void deleteOrderById(Long id);
}
