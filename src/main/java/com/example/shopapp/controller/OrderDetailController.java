package com.example.shopapp.controller;

import com.example.shopapp.components.LocalizationUtils;
import com.example.shopapp.dtos.OrderDetailDto;
import com.example.shopapp.model.OrderDetail;
import com.example.shopapp.repositories.OrderDetailRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/order_details")
@RequiredArgsConstructor
@CrossOrigin("*")

public class OrderDetailController {
    private final LocalizationUtils localizationUtils;
    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody OrderDetailDto orderDetailDto, BindingResult bindingResult) {
        return ResponseEntity.ok().body("created Order Detail successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDto> getOrderDetailById(@PathVariable Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElse(null);
        OrderDetailDto orderDetailDto = modelMapper.map((orderDetail), OrderDetailDto.class);
        return ResponseEntity.ok().body(orderDetailDto);
    }

    @GetMapping("/order/{order_id}")
    public ResponseEntity<?> getOrderDetailByOrderId(@PathVariable Long order_id) {
        return ResponseEntity.ok().body("get Order Detail successfully with orderId "+order_id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@PathVariable Long id
            , @Valid @RequestBody OrderDetailDto newData, BindingResult bindingResult) {
        return ResponseEntity.ok().body("updateOrderDetail successfully with id " + id+",newData "+newData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }


}
