package com.example.shopapp.controller;

import com.example.shopapp.components.LocalizationUtils;
import com.example.shopapp.dtos.OrderDto;
import com.example.shopapp.filters.JwtTokenFilter;
import com.example.shopapp.model.Order;
import com.example.shopapp.repositories.OrderRepository;
import com.example.shopapp.services.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
@CrossOrigin("*")

public class OrderController {
    private final IOrderService orderService;
    private final LocalizationUtils localizationUtils;
    private final OrderRepository orderRepository;

    @PostMapping("/{ids}")
    public ResponseEntity<?> createdOrder( @RequestBody OrderDto orderDto, @PathVariable("ids") List<Long> ids) {
        return ResponseEntity.ok().body(orderService.createOrder(orderDto,ids));
    }

   /* @GetMapping("/users/{user_id}") // Thêm biến đường dẫn user_id
    //GET http://localhost:8088/api/v1/orders/user/4
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId) {
        try {
            List<Order> orders = orderService.getAllOrder(userId);
            return ResponseEntity.ok().body(orders);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
*/
    @GetMapping("/get-all-by-user")
    public ResponseEntity<List<OrderDto>> getAllOrdersByUser() {
        String user = JwtTokenFilter.CURRENT_USER;
           return ResponseEntity.ok().body(orderService.getAllByUser(user)) ;
    }


    @GetMapping("/{id}") // Lấy order theo id của order
    //GET http://localhost:8088/api/v1/orders/user/4
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") Long orderId) {
        try {
            Order existingOrder = orderService.getOrder(orderId);
            return ResponseEntity.ok().body(existingOrder);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    // Công việc của admin
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable Long id,
            @Valid @RequestBody OrderDto orderDto
    ){
        try {
            Order order = orderService.updateOrder(id, orderDto);
            return ResponseEntity.ok().body(order);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    // Xóa mềm ==> cập nhật trường Active = false
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().body("deleted order successfully");
    }

    @GetMapping("get-by-user")
    public ResponseEntity<List<Order>> getOrdersByUser() {
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }
}
