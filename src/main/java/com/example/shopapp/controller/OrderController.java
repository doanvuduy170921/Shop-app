package com.example.shopapp.controller;
import com.example.shopapp.dtos.CategoryDto;
import com.example.shopapp.dtos.OrderDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    @PostMapping("")
    public ResponseEntity<?> createdOrder(@Valid @RequestBody OrderDto orderDto, BindingResult bindingResult) {
        try {
            if(bindingResult.hasErrors()) {
                List<String> errorsMessage = bindingResult.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorsMessage);
            }
            return ResponseEntity.ok().body("created order successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrdersbyId(@PathVariable("user_id") Long user_id) {
        try {

            return ResponseEntity.ok().body("Lấy ra danh sách Order từ user_id");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    // Công việc của admin
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable Long id,
            @Valid @RequestBody OrderDto orderDto
    ){
        return ResponseEntity.ok().body("updated order successfully");
    }

    @DeleteMapping("/{id}")
    // Xóa mềm ==> cập nhật trường Active = false
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body("deleted order successfully");
    }
}
