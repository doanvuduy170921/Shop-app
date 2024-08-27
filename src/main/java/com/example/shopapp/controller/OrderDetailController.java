package com.example.shopapp.controller;

import com.example.shopapp.dtos.CategoryDto;
import com.example.shopapp.dtos.OrderDetailDto;
import com.example.shopapp.dtos.OrderDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody OrderDetailDto orderDetailDto, BindingResult bindingResult) {

        return ResponseEntity.ok().body("created Order Detail successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetailById(@PathVariable Long id) {
        return ResponseEntity.ok().body("getOrderDetailById successfully with id " + id);
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
