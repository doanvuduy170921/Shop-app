package com.example.shopapp.services;

import com.example.shopapp.dtos.OrderDto;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.model.Order;


import java.util.List;

public interface IOrderService {
   Order createOrder(OrderDto orderDto) throws Exception;

   Order getOrder(Long id) throws DataNotFoundException;

   Order updateOrder(Long id, OrderDto orderDto) throws DataNotFoundException;

   void deleteOrder(Long id);

   List<Order> getAllOrder(Long userId);



}
