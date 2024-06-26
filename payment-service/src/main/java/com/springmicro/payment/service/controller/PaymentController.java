package com.springmicro.payment.service.controller;

import com.springmicro.payment.service.entity.Payment;
import com.springmicro.payment.service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService service;

    @GetMapping("/{orderId}")
    public Payment getPayment(@PathVariable String orderId) {
        return service.getByOrderId(orderId);
    }
}
