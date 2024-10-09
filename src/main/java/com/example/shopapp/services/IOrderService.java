package com.example.shopapp.services;

import com.example.shopapp.dtos.OrderDto;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.model.Order;
import com.example.shopapp.responses.OrderRes;


import java.util.List;

public interface IOrderService {
   OrderDto createOrder(OrderDto orderDto, List<Long> ids) ;

   Order getOrder(Long id) throws DataNotFoundException;

   Order updateOrder(Long id, OrderDto orderDto) throws DataNotFoundException;

   void deleteOrder(Long id);

   List<OrderDto> getAllByUser(String username);

   List<Order> getAllOrders();

}
