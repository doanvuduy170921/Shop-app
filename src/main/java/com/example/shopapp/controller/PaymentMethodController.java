package com.example.shopapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/payment-method")
@CrossOrigin("*")
public class PaymentMethodController {

    @GetMapping("/")
    public List<String> getPaymentMethod() {
        return List.of("Thanh toán khi nhận hàng (COD - Cash on Delivery)",
                        "Ví điện tử (Digital Wallets)",
                        "Thanh toán bằng thẻ tín dụng/debit (Credit/Debit Card)");
    }
}
