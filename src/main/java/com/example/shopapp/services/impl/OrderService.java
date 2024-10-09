package com.example.shopapp.services.impl;

import com.example.shopapp.dtos.OrderDto;
import com.example.shopapp.dtos.ProductDto;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.filters.JwtTokenFilter;
import com.example.shopapp.model.*;
import com.example.shopapp.repositories.CartRepository;
import com.example.shopapp.repositories.OrderDetailRepository;
import com.example.shopapp.repositories.OrderRepository;
import com.example.shopapp.repositories.UserRepository;

import com.example.shopapp.responses.OrderDetailRes;
import com.example.shopapp.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private  final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto, List<Long> ids) {
        List<Cart> carts = cartRepository.findAllById(ids);

        String username = JwtTokenFilter.CURRENT_USER;
        User user = userRepository.findByPhoneNumber(username).orElseThrow(() -> new RuntimeException("User not found"));

        // Cập nhật các field của Order từ OrderDto
        Order order = modelMapper.map(orderDto, Order.class);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        order.setActive(true);
        order.setOrderCode(generateOrderCode());
        order.setOrderDate(new Date());
        order.setShippingDate(LocalDate.now().plusDays(2));
        double totalPrice = 0;
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Cart cart : carts) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);  // Gán Order đã được lưu
            orderDetail.setQuantity(cart.getQuantity());
            orderDetail.setPrice((float) cart.getProduct().getPrice());
            orderDetail.setTotalMoney((float) (cart.getQuantity() * cart.getProduct().getPrice()));
            orderDetail.setProduct(cart.getProduct());
            totalPrice += cart.getProduct().getPrice() * cart.getQuantity();
            orderDetails.add(orderDetail);
        }
        order.setTotalMoney((float) totalPrice);
        // 2. Lưu danh sách OrderDetail sau khi đã có Order ID
        order.setOrderDetails(orderDetails);
        order = orderRepository.save(order);
        cartRepository.deleteAllById(ids);
        return modelMapper.map(order, OrderDto.class);  // Lưu lại cả Order và OrderDetail
    }

    @Override
    public List<OrderDto> getAllByUser(String username) {
        User user = userRepository.findByPhoneNumber(username).orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream().map(order -> OrderDto.builder()
                        .address(order.getUser().getAddress())
                        .totalMoney(order.getTotalMoney())
                        .shippingMethod(order.getShippingMethod())
                        .shippingAddress(order.getShippingAddress())
                        .shippingDate(order.getShippingDate())
                        .paymentMethod(order.getPaymentMethod())
                        .orderCode(order.getOrderCode())
                        .orderDate(order.getOrderDate())
                        .orderDetails(order.getOrderDetails().stream()
                                .map(orderDetail -> OrderDetailRes.builder()
                                        .id(orderDetail.getId())
                                        .product(ProductDto.builder()
                                                .name(orderDetail.getProduct().getName())
                                                .price(orderDetail.getProduct().getPrice())
                                                .thumbnail(orderDetail.getProduct().getThumbnail())  // assuming Product has a thumbnail field
                                                .description(orderDetail.getProduct().getDescription())
                                                .categoryId(orderDetail.getProduct().getCategory().getId()) // assuming Product has a Category object
                                                .build())
                                        .price(orderDetail.getPrice())
                                        .quantity(orderDetail.getQuantity())
                                        .totalMoney(orderDetail.getTotalMoney())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    public String generateOrderCode() {
        // Tạo mã đơn hàng với định dạng chỉ có timestamp
        String timeStamp = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        return "#" + timeStamp;
    }
    @Override
    public Order updateOrder(Long id, OrderDto orderDto) throws DataNotFoundException {
        return  null;
    }

    /*@Override
    public List<Order> getAllOrder(Long userId) {
        return orderRepository.findByUserId(userId);
    }*/

    @Override
    public Order getOrder(Long id) throws DataNotFoundException {
        return orderRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("khong tim thay id"));
    }

    @Override
    public void deleteOrder(Long id) {
       Order order = orderRepository.findById(id).orElse(null);
        if(order!=null){
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> getAllOrders() {
        String username = JwtTokenFilter.CURRENT_USER;
        User user = userRepository.findByPhoneNumber(username).orElseThrow(() -> new RuntimeException("User not found"));
        List<Order> list = orderRepository.findByUser(user);
        return list ;
    }
}
