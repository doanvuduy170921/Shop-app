package com.example.shopapp.services.impl;

import com.example.shopapp.dtos.OrderDto;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.model.Order;
import com.example.shopapp.model.OrderStatus;
import com.example.shopapp.model.User;
import com.example.shopapp.repositories.OrderRepository;
import com.example.shopapp.repositories.UserRepository;
import com.example.shopapp.responses.OrderResponse;
import com.example.shopapp.services.IOrderService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private  final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public Order createOrder(OrderDto orderDto) throws Exception {
       // tìm xem trong OrderDto co userId kh
        User user = userRepository.findById(orderDto.getUserId()).orElseThrow(
                ()->new DataNotFoundException("User not found with id :"+orderDto.getUserId()));
        // convert OrderDto -> Order để thêm vào db
        // dùng thư viện Model Maper
        modelMapper.typeMap(OrderDto.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        // cập nhật các field của Order cho OrderDto
        Order order = new Order();
        modelMapper.map(orderDto,order);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        //
        LocalDate shippingDate = orderDto.getShippingDate() ==null ? LocalDate.now() : orderDto.getShippingDate();
        if(shippingDate.isBefore(LocalDate.now())){
            throw new DataNotFoundException("Shipping Date must be least today");
        }
        order.setShippingDate(shippingDate);

        order.setActive(true);
        orderRepository.save(order);

        return order;
    }

    @Override
    public Order updateOrder(Long id, OrderDto orderDto) {
        return null;
    }

    @Override
    public List<Order> getAllOrder(Long userId) {
        return List.of();
    }

    @Override
    public Order getOrder(OrderDto orderDto) {
        return null;
    }

    @Override
    public void deleteOrderById(Long id) {

    }


}
